/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.inoutput.c002_nio.s06_selector;

import com.github.flysium.io.photon.inoutput.c002_nio.s05_nonblocking.NonBlockingServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NIO Server with I/O multiplexing model, single selector
 *
 * @author Sven Augustus
 */
public class NIOMultiplexingServer extends NonBlockingServer {

  public static void main(String[] args) throws Exception {
    NIOMultiplexingServer nioServer = new NIOMultiplexingServer(9090);
    nioServer.start();
  }

  protected static final Logger logger = LoggerFactory.getLogger(NIOMultiplexingServer.class);

  protected Selector selector = null;

  public NIOMultiplexingServer(int port) {
    this(port, 50);
  }

  public NIOMultiplexingServer(int port, int backlog) {
    super(port, backlog);
  }

  @Override
  public String toString() {
    return "NIOMultiplexingServer{" + "port=" + port
        + ", backlog=" + backlog
        + ", selectorProvider="
        + SelectorProvider.provider().getClass().getCanonicalName()
        + '}';
  }

  @Override
  public void start() {
    try {
      initServer();
      select();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    } finally {
      closeServer();
    }
  }

  @Override
  protected void initServer() throws IOException {
    // 打开 ServerSocketChannel, 设置非阻塞模式, 设置TCP Socket参数, 绑定并监听端口
    super.initServer();

    // 创建Selector选择器
    selector = Selector.open();
    // 注册事件，监听客户端连接请求
    server.register(selector, SelectionKey.OP_ACCEPT);
  }


  /**
   * 涉及的系统调用：
   * <li>
   * poll:
   * <pre>
   *  poll([{fd=5, events=POLLIN}, {fd=4, events=POLLIN}], 2, -1) = 1 ([{fd=4, revents=POLLIN}])  //  while (selector.select() > 0)
   *
   *  accept(4,    = 7  //新的客户端
   *
   *  fcntl(7, F_SETFL, O_RDWR|O_NONBLOCK)
   *
   *  poll([{fd=5, events=POLLIN}, {fd=4, events=POLLIN}, {fd=7, events=POLLIN}], 3, -1)  = 1（一个fd有事件） -1（非阻塞下，没有事件）
   * </pre>
   * </li>
   * <li>
   * epoll:
   * <pre>
   *  epoll_create(256)                       = 7 (epfd)
   *  epoll_ctl(7, EPOLL_CTL_ADD, 4,
   *  epoll_wait(7, {{EPOLLIN, {u32=4, u64=2216749036554158084}}}, 4096, -1) = 1 //  while (selector.select() > 0)
   *
   *  accept(4      =  8  //新的客户端
   *
   *  fcntl(8, F_SETFL, O_RDWR|O_NONBLOCK)
   *  epoll_ctl(7, EPOLL_CTL_ADD, 8, {EPOLLIN,
   *
   *  epoll_wait(7,
   * </pre>
   * </li>
   */
  private void select() throws IOException {
    for (; ; ) {
      // 无论是否有事件发生，selector每隔timeout被唤醒一次
      if (selector.select() == 0) {
        continue;
      }
      Set<SelectionKey> selectedKeys = selector.selectedKeys();
      Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
      while (keyIterator.hasNext()) {
        SelectionKey key = keyIterator.next();
        keyIterator.remove();

        if (!key.isValid()) {
          continue;
        }

        if (key.isAcceptable()) {
          readyAccepted(key);
        } else if (key.isReadable()) {
          readyRead(key);
        } else if (key.isWritable()) {
          readyWrite(key);
        }
      }
    }
  }

  protected void readyAccepted(SelectionKey key) {
    doAccepted(key);
  }

  protected void doAccepted(SelectionKey key) {
    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
    try {
      SocketChannel client = ssc.accept();
      logger.info("accept new client：" + getRemoteAddress(client));

      // 设置非阻塞模式, 设置TCP Socket参数
      super.accepted(client);

      // 注册读操作 , 以进行下一步的读操作
      ByteBuffer buffer = ByteBuffer.allocate(8192);
      client.register(selector, SelectionKey.OP_READ, buffer);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  protected void readyRead(SelectionKey key) {
    doRead(key);
  }

  protected void doRead(SelectionKey key) {
    SocketChannel client = (SocketChannel) key.channel();
    ByteBuffer buffer = (ByteBuffer) key.attachment();

    doRead(client, buffer,
        (c, buf, readCount) -> {
          // 转换，并打印请求内容
          String request;
          try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(buffer.array(), 0, readCount);
            request = baos.toString();
          }
          logger.info("readied something from " + getRemoteAddress(client) + ", count: " + readCount
              + " data: " + request);

          // FIXME 模拟业务逻辑处理时间耗时
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
          }

          // 写入响应内容
          buffer.clear();
          buffer.put(("recv->" + request).getBytes());

          client.register(selector, SelectionKey.OP_WRITE, buffer);
          selector.wakeup();

          return true;// break;
        }, (c, buf, readCount) -> {
          logger.warn("readied nothing from " + getRemoteAddress(client) + "! ");
          return true;
        });
  }

  protected void readyWrite(SelectionKey key) {
    toWrite(key);
  }

  protected void toWrite(SelectionKey key) {
    SocketChannel client = (SocketChannel) key.channel();
    ByteBuffer buffer = (ByteBuffer) key.attachment();

    doWrite(client, buffer, (c, buf) -> {
      client.register(selector, SelectionKey.OP_READ, buffer);
      selector.wakeup();
    }, (c, buf, writeCount) -> {
      // 解决处理 Send-Q 满了，暂时无法写出的情况，避免导致 CPU 100%
      key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
      selector.wakeup();
      return true;// break;
    });
  }

}
