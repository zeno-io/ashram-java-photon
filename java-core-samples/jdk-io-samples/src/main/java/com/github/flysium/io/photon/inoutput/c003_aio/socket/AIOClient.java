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

package com.github.flysium.io.photon.inoutput.c003_aio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client
 *
 * @author Sven Augustus
 */
public class AIOClient implements CompletionHandler<Void, AIOClient>, Runnable {

  public static void main(String[] args) throws Exception {
    new Thread(new AIOClient("127.0.0.1", 8010), "AIOClient-001").start();
    new Thread(new AIOClient("127.0.0.1", 8010), "AIOClient-002").start();
    new Thread(new AIOClient("127.0.0.1", 8010), "AIOClient-003").start();
    new Thread(new AIOClient("127.0.0.1", 8010), "AIOClient-004").start();

    while (Thread.activeCount() > 1) {
      TimeUnit.MILLISECONDS.sleep(10);
    }
  }

  // for test
  private final byte[] request = String.valueOf(new Random().nextInt(1000)).getBytes();

  private static final Logger logger = LoggerFactory.getLogger(AIOClient.class);
  private final String host;
  private final int port;
  private volatile AsynchronousSocketChannel channel;

  public AIOClient(String host, int port) {
    this.host = host;
    this.port = port;
    try {
      // 初始化一个AsynchronousSocketChannel
      channel = AsynchronousSocketChannel.open();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Override
  public void run() {
    // 连接服务端，并将自身作为连接成功时的回调handler
    channel.connect(new InetSocketAddress(host, port), this, this);
  }

  /**
   * 连接服务端成功时的回调
   */
  @Override
  public void completed(Void result, AIOClient attachment) {
    // 分配写缓存区
    ByteBuffer write = ByteBuffer.allocate(request.length);
    // 往写缓存区写请求body
    write.put(request);
    write.flip();
    // 将缓存中的数据写到channel，同时使用匿名内部类做完成后回调
    channel.write(write, write, new ResponseCompletionHandler());
  }

  /**
   * 连接服务端失败
   */
  @Override
  public void failed(Throwable exc, AIOClient attachment) {
    {
      try {
        channel.close();
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  class ResponseCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    @Override
    public void completed(Integer result, ByteBuffer byteBuffer) {
      // 如果缓存数据中还有数据，接着写
      if (byteBuffer.hasRemaining()) {
        channel.write(byteBuffer, byteBuffer, this);
      } else {
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        //读取服务端的返回到缓存，采用匿名内部类做写完缓存后的回调handler
        channel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {

          /**
           * 从缓存中读取数据，做业务处理
           */
          @Override
          public void completed(Integer result, ByteBuffer buffer) {
            buffer.flip();
            byte[] response = new byte[buffer.remaining()];
            buffer.get(response);
            String responseString = new String(response, StandardCharsets.UTF_8);

            logger.info("客户端" + getLocalAddress(channel)
                + "，请求：" + new String(request)
                + "，响应：" + responseString);
          }

          /**
           * 从缓存读取数据失败
           * 关闭client，释放channel相关联的一切资源
           */
          @Override
          public void failed(Throwable exc, ByteBuffer attachment) {
            try {
              channel.close();
            } catch (IOException e) {
              logger.error(e.getMessage(), e);
            }
          }
        });
      }

    }

    /**
     * 缓存写入channel失败 关闭client，释放channel相关联的一切资源
     */
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
      {
        try {
          channel.close();
        } catch (IOException e) {
          logger.error(e.getMessage(), e);
        }
      }
    }
  }

  private static SocketAddress getLocalAddress(AsynchronousSocketChannel channel) {
    try {
      return channel.getLocalAddress();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
