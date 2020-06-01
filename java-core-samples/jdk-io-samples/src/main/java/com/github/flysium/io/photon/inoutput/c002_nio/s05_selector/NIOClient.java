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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client
 *
 * @author Sven Augustus
 */
@SuppressWarnings("unused")
public class NIOClient implements Runnable {

  public static void main(String[] args) throws Exception {
    new Thread(new NIOClient("127.0.0.1", 9011, 1000), "NIOClient-001").start();
    new Thread(new NIOClient("127.0.0.1", 9011, 1000), "NIOClient-002").start();
    new Thread(new NIOClient("127.0.0.1", 9011, 1000), "NIOClient-003").start();
    new Thread(new NIOClient("127.0.0.1", 9011, 1000), "NIOClient-004").start();

    while (Thread.activeCount() > 1) {
      TimeUnit.MILLISECONDS.sleep(10);
    }
  }

  // for test
  private final byte[] request = String.valueOf(new Random().nextInt(1000)).getBytes();

  private static final Logger logger = LoggerFactory.getLogger(NIOClient.class);
  private final String host;
  private final int port;
  private final int selectTimeout;
  private volatile SocketChannel channel;

  public NIOClient(String host, int port, int selectTimeout) {
    this.host = host;
    this.port = port;
    this.selectTimeout = selectTimeout;
  }

  @Override
  public void run() {
    try {
      // 创建Selector选择器
      Selector selector = Selector.open();
      // 打开SocketChannel
      channel = SocketChannel.open();
      // 设置非阻塞模式，read的时候就不再阻塞
      channel.configureBlocking(false);
      // tcp连接网络
      if (channel.connect(new InetSocketAddress(host, port))) {
        channel.register(selector, SelectionKey.OP_READ);
        // write
        write(channel, request);
      } else {
        channel.register(selector, SelectionKey.OP_CONNECT);
      }
      int nextOffset = 0;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      boolean stop = false;
      while (!stop) {
        // 无论是否有事件发生，selector每隔timeout被唤醒一次
        if (selector.select(selectTimeout) == 0) {
          continue;
        }
        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> it = keys.iterator();
        SelectionKey key = null;
        while (it.hasNext()) {
          key = it.next();
          it.remove();
          SocketChannel sc = (SocketChannel) key.channel();

          if (key.isConnectable()) {
            // 连接服务器成功
            if (channel.finishConnect()) {
              sc.register(selector, SelectionKey.OP_READ);

              // write
              write(sc, request);
            } else {
              logger.error("连接失败，服务器拒绝服务");
              break;
            }
          } else if (key.isReadable()) {
            // read
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int readBytes = sc.read(readBuffer);

            if (readBytes > 0) {
              readBuffer.flip();
              outputStream.write(readBuffer.array(), nextOffset, readBytes);
              nextOffset += readBytes;
              readBuffer.clear();

              stop = true;
            } else if (readBytes < 0) {
              logger.warn("服务端断开...");
              // 对端链路关闭
              close(sc, key);
            } else {
              key.interestOps(SelectionKey.OP_READ);
              logger.info("继续...");
            }
          }
        }
      }
      byte[] response = outputStream.toByteArray();
      logger.info("客户端" + channel.socket().getInetAddress()
          + "端口" + channel.socket().getPort()
          + "，请求：" + new String(request)
          + "，响应：" + new String(response));
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    } finally {
      close(channel);
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

}
