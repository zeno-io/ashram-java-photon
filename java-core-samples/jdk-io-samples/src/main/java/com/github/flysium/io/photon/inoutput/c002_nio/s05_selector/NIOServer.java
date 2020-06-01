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

package com.github.flysium.io.photon.inoutput.c002_nio.s05_selector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NIOServer，使用Selector模式
 *
 * @author Sven Augustus
 */
public class NIOServer {

  public static void main(String[] args) throws Exception {
    NIOServer nioServer = new NIOServer(9011, 10);
    nioServer.initServer();
    nioServer.listen();
  }

  private static final Logger logger = LoggerFactory.getLogger(NIOServer.class);

  private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(4, 8, 60,
      TimeUnit.SECONDS,
      new ArrayBlockingQueue<>(100),
      Executors.defaultThreadFactory(), new CallerRunsPolicy());

  private final int port;
  private volatile Selector selector = null;
  private final int selectTimeout;
  private final BlockingQueue<RegisterEvent> registerEvents = new ArrayBlockingQueue<RegisterEvent>(
      1000);

  public NIOServer(int port, int selectTimeout) {
    this.port = port;
    this.selectTimeout = selectTimeout;
  }

  public void initServer() throws IOException {
    // 打开 ServerSocketChannel
    ServerSocketChannel channel = ServerSocketChannel.open();
    // 设置非阻塞模式，read的时候就不再阻塞
    channel.configureBlocking(false);
    // 将 ServerSocket 绑定到特定地址（IP 地址和端口号）
    channel.bind(new InetSocketAddress(port));

    // 创建Selector选择器
    selector = Selector.open();
    // 注册事件，监听客户端连接请求
    channel.register(selector, SelectionKey.OP_ACCEPT);

    // 正确注册Channel和更新interest
    EXECUTOR.submit(new RegisterHandler());
  }

  public void listen() throws IOException {
    while (true) {
      // 无论是否有事件发生，selector每隔timeout被唤醒一次
      if (selector.select(selectTimeout) == 0) {
        continue;
      }
      Set<SelectionKey> selectedKeys = selector.selectedKeys();
      Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
      while (keyIterator.hasNext()) {
        SelectionKey key = keyIterator.next();
        keyIterator.remove();

        if (key.isAcceptable()) {
          SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
          // 设置非阻塞模式
          sc.configureBlocking(false);
          // 注册读操作 , 以进行下一步的读操作
//          socketChannel.register(key.selector(), SelectionKey.OP_READ);
          registerEvents.offer(new RegisterEvent(sc, SelectionKey.OP_READ));
        } else if (key.isValid() && key.isReadable()) {
          SocketChannel sc = (SocketChannel) key.channel();
//          key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
          registerEvents.offer(new RegisterEvent(sc, key.interestOps() & (~SelectionKey.OP_READ)));

          EXECUTOR.execute(new RequestHandler(key));
        }
      }
    }
  }

  private void write(SocketChannel channel, byte[] content) throws IOException {
    ByteBuffer writeBuf = ByteBuffer.allocate(content.length);
    writeBuf.put(content);
    writeBuf.flip();
    channel.write(writeBuf);
  }

  private void close(SocketChannel channel) {
    try {
      if (channel != null) {
        channel.close();
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  private void close(SocketChannel channel, SelectionKey key) {
    close(channel);
    key.cancel();
  }

  class RegisterHandler implements Runnable {

    @Override
    public void run() {
      while (true) {
        try {
          RegisterEvent registerEvent = registerEvents.poll(10, TimeUnit.SECONDS);
          if (registerEvent != null) {
            registerEvent.channel.register(selector, registerEvent.ops, registerEvent.attachment);

            selector.wakeup();
          }
        } catch (ClosedChannelException | InterruptedException e) {
          logger.error(e.getMessage(), e);
        }
      }
    }
  }

  class RequestHandler implements Runnable {

    private final SelectionKey key;
    private final SocketChannel channel;

    RequestHandler(SelectionKey key) {
      this.key = key;
      this.channel = (SocketChannel) key.channel();
    }

    @Override
    public void run() {
      try {
        // read
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int readBytes, nextOffset = 0;
        byte[] request;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
          while ((readBytes = channel.read(readBuffer)) > 0) {
            readBuffer.flip();
            baos.write(readBuffer.array(), nextOffset, readBytes);
            nextOffset += readBytes;
            readBuffer.clear();
          }
          request = baos.toByteArray();
        }

        // compute
        String requestString = new String(request);
        if (requestString.length() > 0) {
          //            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
//            key.selector().wakeup();
          registerEvents.offer(new RegisterEvent(channel,
              key.interestOps() & (~SelectionKey.OP_READ)));

          byte[] response;
          try {
            int integer = Integer.parseInt(requestString);
            response = String.valueOf(integer * 2).getBytes();
          } catch (NumberFormatException e) {
            response = ("Error Request" + requestString).getBytes();
          }

          // write
          write(channel, response);

          logger.info("客户端" + channel.socket().getInetAddress()
              + "端口" + channel.socket().getPort()
              + "，请求：" + new String(request)
              + "，响应：" + new String(response));
        } else if (readBytes < 0) {
          logger.warn("客户端" + channel.socket().getInetAddress()
              + "端口" + channel.socket().getPort() + "，断开...");
          close(channel, key);
        }
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
        close(channel, key);
      }
    }

  }

  static class RegisterEvent {

    private final SocketChannel channel;

    private final int ops;

    private final Object attachment;

    RegisterEvent(SocketChannel channel, int ops) {
      this(channel, ops, null);
    }

    RegisterEvent(SocketChannel channel, int ops, Object attachment) {
      this.channel = channel;
      this.ops = ops;
      this.attachment = attachment;
    }
  }

}
