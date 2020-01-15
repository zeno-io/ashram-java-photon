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
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server
 *
 * @author Sven Augustus
 */
public class Nio2Server implements Runnable {

  public static void main(String[] args) {
    new Thread(new Nio2Server(8010), "AIO-Nio2Server-001").start();
  }

  private final int port;
  private volatile AsynchronousServerSocketChannel acceptChannel;

  // 仅为计数工具类，防止主线程main启动完成后关闭
  private CountDownLatch latch;

  public Nio2Server(int port) {
    this.port = port;
  }

  @Override
  public void run() {
    latch = new CountDownLatch(1);

    //1. 创建一个线程池
    ExecutorService es = Executors.newCachedThreadPool();

    try {
      //2. 创建异步通道群组
      AsynchronousChannelGroup tg = AsynchronousChannelGroup.withCachedThreadPool(es, 1);
      //3. 创建服务端异步通道
      acceptChannel = AsynchronousServerSocketChannel.open(tg);
    } catch (IOException e) {
      e.printStackTrace();
    }

    //4. 绑定监听端口
    try {
      acceptChannel.bind(new InetSocketAddress(port));
    } catch (IOException e) {
      e.printStackTrace();
    }

    //5. 监听连接，传入回调类处理连接请求
    acceptChannel.accept(this, new AcceptHandler());

    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  //AcceptHandler 类实现了 CompletionHandler 接口的 completed 方法。它还有两个模板参数，第一个是异步通道，第二个就是 Nio2Server 本身
  public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Nio2Server> {

    // 具体处理连接请求的就是 completed 方法，它有两个参数：第一个是异步通道，第二个就是上面传入的 NioServer 对象
    @Override
    public void completed(AsynchronousSocketChannel channel, Nio2Server attachment) {
      // 调用 accept 方法继续接收其他客户端的请求
      attachment.acceptChannel.accept(attachment, this);

      //1. 先分配好 Buffer，告诉内核，数据拷贝到哪里去
      ByteBuffer buf = ByteBuffer.allocate(1024);

      //2. 调用 read 函数读取数据，除了把 buf 作为参数传入，还传入读回调类
      // 异步读操作，参数的定义：第一个参数：接收缓冲区，用于异步从channel读取数据包；
      // 第二个参数：异步channel携带的附件，通知回调的时候作为入参参数，这里是作为 ReadCompletionHandler 的入参
      // 通知回调的业务handler，也就是数据从channel读到ByteBuffer完成后的回调handler，这里是ReadCompletionHandler
      channel.read(buf, buf, new ReadCompletionHandler(channel));
    }

    @Override
    public void failed(Throwable exc, Nio2Server attachment) {
      exc.printStackTrace();
      attachment.latch.countDown();
    }

  }

  class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private final AsynchronousSocketChannel channel;

    ReadCompletionHandler(AsynchronousSocketChannel channel) {
      this.channel = channel;
    }

    // 读取到消息后的处理
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
      //attachment 就是数据，调用 flip 操作，其实就是把读的位置移动最前面
      attachment.flip();

      // 读取数据
      byte[] body = new byte[attachment.remaining()];
      attachment.get(body);
      String req = new String(body, StandardCharsets.UTF_8);
      System.out.println("requestBody=" + req);
      String currentTime =
          "query time order".equals(req) ? new Date(System.currentTimeMillis()).toString()
              : "bad order";

      doWrite(currentTime);
    }

    /**
     * 往客户端的写操作
     */

    public void doWrite(String currentTime) {
      if (currentTime != null && currentTime.trim().length() > 0) {
        byte[] bytes = currentTime.getBytes();
        // 分配一个写缓存
        ByteBuffer write = ByteBuffer.allocate(bytes.length);
        System.out.println("responseBody=" + currentTime);
        // 将返回数据写入缓存
        write.put(bytes);
        write.flip();
        // 将缓存写进channel
        channel.write(write, write, new CompletionHandler<Integer, ByteBuffer>() {
          @Override
          public void completed(Integer result, ByteBuffer buffer) {
            //如果发现还有数据没写完，继续写
            if (buffer.hasRemaining()) {
              channel.write(buffer, buffer, this);
            }
          }

          @Override
          public void failed(Throwable exc, ByteBuffer attachment) {
            try {
              //写失败，关闭channel，并释放与channel相关联的一切资源
              channel.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
      }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
      try {
        //读，关闭channel，并释放与channel相关联的一切资源
        channel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

}
