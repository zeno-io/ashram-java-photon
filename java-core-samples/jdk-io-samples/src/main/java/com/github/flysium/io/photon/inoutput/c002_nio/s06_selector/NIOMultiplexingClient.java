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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NIO Client with multiplexing
 *
 * @author Sven Augustus
 */
@SuppressWarnings("unused")
public class NIOMultiplexingClient implements Runnable {

  public static void main(String[] args) throws Exception {
    Supplier<String> stringSupplier = () -> {
      return "" + new Random().nextInt(1000);
    };
    new Thread(new NIOMultiplexingClient("127.0.0.1", 9090, 1000, stringSupplier), "NIOClient-001")
        .start();
    new Thread(new NIOMultiplexingClient("127.0.0.1", 9090, 1000, stringSupplier), "NIOClient-002")
        .start();
    new Thread(new NIOMultiplexingClient("127.0.0.1", 9090, 1000, stringSupplier), "NIOClient-003")
        .start();
    new Thread(new NIOMultiplexingClient("127.0.0.1", 9090, 1000, stringSupplier), "NIOClient-004")
        .start();

    System.in.read();
  }

  private static final Logger logger = LoggerFactory.getLogger(NIOMultiplexingClient.class);
  private final String host;
  private final int port;
  private final int selectTimeout;

  // for test
  private final Supplier<String> supplier;

  private SocketChannel client;
  private Selector selector;
  private boolean stop = false;

  public NIOMultiplexingClient(String host, int port, Supplier<String> supplier) {
    this(host, port, 500, supplier);
  }

  public NIOMultiplexingClient(String host, int port, int selectTimeout,
      Supplier<String> supplier) {
    this.host = host;
    this.port = port;
    this.selectTimeout = selectTimeout;
    this.supplier = supplier;
  }

  @Override
  public void run() {
    try {
      init();
      select();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    } finally {
      close();
    }
  }

  private void init() throws IOException {
    // 打开SocketChannel
    client = SocketChannel.open();
    // 设置非阻塞模式，read的时候就不再阻塞
    client.configureBlocking(false);

    setSocketOptions(client);

    // 创建Selector选择器
    selector = Selector.open();

    // 连接服务器
    if (client.connect(new InetSocketAddress(host, port))) {
      client.register(selector, SelectionKey.OP_READ);
    } else {
      client.register(selector, SelectionKey.OP_CONNECT);
    }
  }

  protected void setSocketOptions(SocketChannel client) throws IOException {
    client.setOption(StandardSocketOptions.TCP_NODELAY, true);
    client.setOption(StandardSocketOptions.SO_LINGER, 100);
  }

  private void select() throws IOException {
    while (!stop) {
      // 无论是否有事件发生，selector每隔timeout被唤醒一次
      if (selector.select(selectTimeout) == 0) {
        continue;
      }
      Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
      while (keyIterator.hasNext()) {
        SelectionKey key = keyIterator.next();
        keyIterator.remove();

        SocketChannel client = (SocketChannel) key.channel();

        if (!key.isValid()) {
          continue;
        }
        if (key.isConnectable()) {
          // 连接服务器成功
          if (client.finishConnect()) {
            readyWrite();

            client.register(selector, SelectionKey.OP_READ);
          } else {
            logger.error("连接失败，服务器拒绝服务");
            break;
          }
        } else if (key.isReadable()) {
          readyRead();
        }
      }
    }
  }

  protected void readyWrite() throws IOException {
    if (supplier == null) {
      return;
    }
    String buf = supplier.get();

    logger.info("write...");

    ByteBuffer buffer = ByteBuffer.wrap(buf.getBytes());

    client.write(buffer);
  }

  protected void readyRead() throws IOException {
    int nextOffset = 0;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
      ByteBuffer buffer = ByteBuffer.allocate(8092);
      buffer.clear();
      int readCount = 0;
      while (true) {
        readCount = client.read(buffer);
        if (readCount > 0) {
          baos.write(buffer.array(), nextOffset, readCount);
          nextOffset += readCount;
          String response = baos.toString();
          logger.info("readied something, count: " + readCount + " data: " + response);

        } else if (readCount == 0) {
          logger.warn("readied nothing ! ");
          break;
        } else {
          logger.warn("readied -1...");
          close();
          break;
        }
      }
    }
  }

  private void close() {
    if (stop) {
      return;
    }
    try {
      logger.warn("close.....");
      if (client != null) {
        client.close();
      }
      stop = true;
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

}
