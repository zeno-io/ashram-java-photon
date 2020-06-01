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
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server
 *
 * @author Sven Augustus
 */
public class AIOServer {

  public static void main(String[] args) throws Exception {
    AIOServer aioServer = new AIOServer(8010);
    aioServer.initServer();
    aioServer.listen();
  }

  private static final Logger logger = LoggerFactory.getLogger(AIOServer.class);

  private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(4, 8, 60,
      TimeUnit.SECONDS,
      new ArrayBlockingQueue<>(100),
      Executors.defaultThreadFactory(), new CallerRunsPolicy());

  private final int port;
  private volatile AsynchronousServerSocketChannel acceptChannel;

  public AIOServer(int port) {
    this.port = port;
  }

  public void initServer() throws IOException {
    // 1. 创建异步通道群组
    AsynchronousChannelGroup tg = AsynchronousChannelGroup.withCachedThreadPool(EXECUTOR, 1);
    // 2. 创建服务端异步通道
    acceptChannel = AsynchronousServerSocketChannel.open(tg);
    // 3. 绑定监听端口
    acceptChannel.bind(new InetSocketAddress(port));
  }

  public void listen() throws IOException {
    // 监听连接，传入回调类处理连接请求
    acceptChannel.accept(this, new AcceptHandler());
  }

  // AcceptHandler 类实现了 CompletionHandler 接口的 completed 方法。它还有两个模板参数，第一个是异步通道，第二个就是 Nio2Server 本身
  static class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServer> {

    // 具体处理连接请求的就是 completed 方法，它有两个参数：第一个是异步通道，第二个就是上面传入的 NioServer 对象
    @Override
    public void completed(AsynchronousSocketChannel channel, AIOServer attachment) {
      // 调用 accept 方法继续接收其他客户端的请求
      attachment.acceptChannel.accept(attachment, this);

      // 1. 先分配好 Buffer，告诉内核，数据拷贝到哪里去
      ByteBuffer buf = ByteBuffer.allocate(1024);

      // 2. 调用 read 函数读取数据，除了把 buf 作为参数传入，还传入读回调类
      // 异步读操作，参数的定义：第一个参数：接收缓冲区，用于异步从channel读取数据包；
      // 第二个参数：异步channel携带的附件，通知回调的时候作为入参参数，这里是作为 ReadCompletionHandler 的入参
      // 通知回调的业务handler，也就是数据从channel读到ByteBuffer完成后的回调handler，这里是ReadCompletionHandler
      channel.read(buf, buf, new RequestCompletionHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AIOServer attachment) {
      logger.error(exc.getMessage(), exc);
    }
  }

  static class RequestCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private final AsynchronousSocketChannel channel;

    RequestCompletionHandler(AsynchronousSocketChannel channel) {
      this.channel = channel;
    }

    // 读取到消息后的处理
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
      // attachment 就是数据，调用 flip 操作，其实就是把读的位置移动最前面
      attachment.flip();

      // 读取数据
      byte[] request = new byte[attachment.remaining()];
      attachment.get(request);

      // compute
      String requestString = new String(request, StandardCharsets.UTF_8);
      if (requestString.length() > 0) {
        byte[] response;
        try {
          int integer = Integer.parseInt(requestString);
          response = String.valueOf(integer * 2).getBytes();
        } catch (NumberFormatException e) {
          response = ("Error Request" + requestString).getBytes();
        }

        doWrite(response);

        logger.info("客户端" + getRemoteAddress(channel)
            + "，请求：" + new String(request)
            + "，响应：" + new String(response));
      }
    }

    /**
     * 往客户端的写操作
     */
    public void doWrite(byte[] response) {
      if (response == null) {
        return;
      }
      // 分配一个写缓存
      ByteBuffer write = ByteBuffer.allocate(response.length);
      // 将返回数据写入缓存
      write.put(response);
      write.flip();
      // 将缓存写进channel
      channel.write(write, write, new CompletionHandler<Integer, ByteBuffer>() {

        @Override
        public void completed(Integer result, ByteBuffer buffer) {
          // 如果发现还有数据没写完，继续写
          if (buffer.hasRemaining()) {
            channel.write(buffer, buffer, this);
          }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
          try {
            // 写失败，关闭channel，并释放与channel相关联的一切资源
            channel.close();
          } catch (IOException e) {
            logger.error(e.getMessage(), e);
          }
        }
      });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
      try {
        // 读，关闭channel，并释放与channel相关联的一切资源
        channel.close();
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  private static SocketAddress getRemoteAddress(AsynchronousSocketChannel channel) {
    try {
      return channel.getRemoteAddress();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
