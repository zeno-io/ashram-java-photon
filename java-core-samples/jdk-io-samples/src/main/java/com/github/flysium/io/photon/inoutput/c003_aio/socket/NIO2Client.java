/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
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
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * Client
 *
 * @author Sven Augustus
 */
public class NIO2Client implements CompletionHandler<Void, NIO2Client>, Runnable {

  public static void main(String[] args) {
    NIO2Client client = new NIO2Client("127.0.0.1", 8010);
    client.setRequestBody("query time order".getBytes());
//    client.setRequestBody("test more".getBytes());
    new Thread(client, "AIO-NIO2Client-001").start();
  }

  private final String host;
  private final int port;
  private volatile AsynchronousSocketChannel channel;

  // 仅为计数工具类，防止主线程main启动完成后关闭
  private CountDownLatch latch;

  private byte[] requestBody = new byte[0];

  public NIO2Client(String host, int port) {
    this.host = host;
    this.port = port;
    try {
      //初始化一个AsynchronousSocketChannel
      channel = AsynchronousSocketChannel.open();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public byte[] getRequestBody() {
    return requestBody;
  }

  public void setRequestBody(byte[] requestBody) {
    this.requestBody = requestBody;
  }

  @Override
  public void run() {
    latch = new CountDownLatch(1);

    // 连接服务端，并将自身作为连接成功时的回调handler
    channel.connect(new InetSocketAddress(host, port), this, this);
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 连接服务端成功时的回调
   */
  @Override
  public void completed(Void result, NIO2Client attachment) {
    //请求参数
    byte[] req = requestBody;
    //分配写缓存区
    ByteBuffer write = ByteBuffer.allocate(req.length);
    //往写缓存区写请求body
    write.put(req);
    write.flip();
    //将缓存中的数据写到channel，同时使用匿名内部类做完成后回调
    channel.write(write, write, new CompletionHandler<Integer, ByteBuffer>() {
      @Override
      public void completed(Integer result, ByteBuffer byteBuffer) {
        //如果缓存数据中还有数据，接着写
        if (byteBuffer.hasRemaining()) {
          channel.write(byteBuffer, byteBuffer, this);
        } else {
          ByteBuffer readBuffer = ByteBuffer.allocate(1024);
          //读取服务端的返回到缓存，采用匿名内部类做写完缓存后的回调handler
          channel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            /**
             * 从缓存中读取数据，做业务处理
             * @param result
             * @param buffer
             */
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
              buffer.flip();
              byte[] bytes = new byte[buffer.remaining()];
              buffer.get(bytes);
              String body;
              try {
                body = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("responseBody=" + body);
              } finally {
                latch.countDown();
              }
            }

            /**
             * 从缓存读取数据失败
             * 关闭client，释放channel相关联的一切资源
             * @param exc
             * @param attachment
             */
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
              try {
                channel.close();
              } catch (IOException e) {
                e.printStackTrace();
              } finally {
                latch.countDown();
              }
            }
          });
        }

      }

      /**
       * 缓存写入channel失败
       * 关闭client，释放channel相关联的一切资源
       * @param exc
       * @param attachment
       */
      @Override
      public void failed(Throwable exc, ByteBuffer attachment) {
        {
          try {
            channel.close();
          } catch (IOException e) {
            e.printStackTrace();
          } finally {
            latch.countDown();
          }
        }
      }
    });
  }

  /**
   * 连接服务端失败
   */
  @Override
  public void failed(Throwable exc, NIO2Client attachment) {
    {
      try {
        channel.close();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        latch.countDown();
      }
    }
  }

}
