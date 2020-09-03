/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c003_aio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
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
    AIOServer aioServer = new AIOServer(9090);
    aioServer.start();
  }

  private static final Logger logger = LoggerFactory.getLogger(AIOServer.class);

  private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(4, 8, 60,
      TimeUnit.SECONDS,
      new ArrayBlockingQueue<>(1000),
      Executors.defaultThreadFactory(), new CallerRunsPolicy());

  private final int port;
  private final int backlog;

  private AsynchronousServerSocketChannel server;

  public AIOServer(int port) {
    this(port, 50);
  }

  public AIOServer(int port, int backlog) {
    this.port = port;
    this.backlog = backlog;
  }

  public void start() {
    try {
      initServer();
      accept();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  private void initServer() throws IOException {
    // 1. 创建异步通道群组
    AsynchronousChannelGroup tg = AsynchronousChannelGroup.withCachedThreadPool(EXECUTOR, 1);
    // 2. 创建服务端异步通道
    server = AsynchronousServerSocketChannel.open(tg);
    setServerSocketOptions(server);
    // 3. 绑定监听端口
    server.bind(new InetSocketAddress(port), backlog);
  }

  private void accept() {
    // 监听连接，传入回调类处理连接请求
    server.accept(this, new AcceptHandler());
  }

  protected void setServerSocketOptions(AsynchronousServerSocketChannel server) throws IOException {
    server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
  }

  protected void setClientSocketOptions(AsynchronousSocketChannel client) throws IOException {
    client.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
    client.setOption(StandardSocketOptions.TCP_NODELAY, true);
    client.setOption(StandardSocketOptions.SO_LINGER, 100);
  }

  // AcceptHandler 类实现了 CompletionHandler 接口的 completed 方法。它还有两个模板参数，第一个是异步通道，第二个就是 Nio2Server 本身
  class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServer> {

    // 具体处理连接请求的就是 completed 方法，它有两个参数：第一个是异步通道，第二个就是上面传入的 NioServer 对象
    @Override
    public void completed(AsynchronousSocketChannel client, AIOServer thatServer) {
      // 调用 accept 方法继续接收其他客户端的请求
      thatServer.server.accept(thatServer, this);

      try {
        setClientSocketOptions(client);
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
      }

      // 1. 先分配好 Buffer，告诉内核，数据拷贝到哪里去
      ByteBuffer buf = ByteBuffer.allocate(1024);

      // 2. 调用 read 函数读取数据，除了把 buf 作为参数传入，还传入读回调类
      // 异步读操作，参数的定义：第一个参数：接收缓冲区，用于异步从channel读取数据包；
      // 第二个参数：异步channel携带的附件，通知回调的时候作为入参参数，这里是作为 ReadCompletionHandler 的入参
      // 通知回调的业务handler，也就是数据从channel读到ByteBuffer完成后的回调handler，这里是ReadCompletionHandler
      client.read(buf, buf, new ReadHandler(client));
    }

    @Override
    public void failed(Throwable exc, AIOServer attachment) {
      logger.error(exc.getMessage(), exc);
      close();
    }
  }

  static class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    private final AsynchronousSocketChannel client;

    ReadHandler(AsynchronousSocketChannel client) {
      this.client = client;
    }

    // 读取到消息后的处理
    @Override
    public void completed(Integer readCount, ByteBuffer buffer) {
      if (readCount > 0) {
        // attachment 就是数据，调用 flip 操作，其实就是把读的位置移动最前面
        buffer.flip();

        // 读取数据
        byte[] request = new byte[buffer.remaining()];
        buffer.get(request);
        String requestString = new String(request, StandardCharsets.UTF_8);

        logger.info("readied something from " + getRemoteAddress(client) + ", count: " + readCount
            + " data: " + requestString);

        // 返回响应内容
        channelWrite(("recv->" + requestString).getBytes());

      } else if (readCount == 0) {
        logger.warn("readied nothing from " + getRemoteAddress(client) + "! ");
      } else {
        logger.warn("readied -1 from " + getRemoteAddress(client) + "...");
        closeClient(client);
      }
    }

    /**
     * 往客户端的写操作
     */
    public void channelWrite(byte[] response) {
      if (response == null) {
        return;
      }
      // 分配一个写缓存
      ByteBuffer write = ByteBuffer.allocate(response.length);
      // 将返回数据写入缓存
      write.put(response);
      write.flip();
      // 将缓存写进channel
      client.write(write, write, new WriteHandler(client));
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
      logger.error(exc.getMessage(), exc);
      closeClient(client);
    }

  }

  static class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {

    private final AsynchronousSocketChannel client;

    WriteHandler(AsynchronousSocketChannel client) {
      this.client = client;
    }

    @Override
    public void completed(Integer readCount, ByteBuffer buffer) {
      // 如果发现还有数据没写完，继续写
      if (buffer.hasRemaining()) {
        client.write(buffer, buffer, this);
      }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
      logger.error(exc.getMessage(), exc);
      closeClient(client);
    }
  }

  protected void close() {
    logger.warn("close server.....");
    try {
      if (server != null) {
        server.close();
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  private static void closeClient(AsynchronousSocketChannel client) {
    logger.warn("close client：" + getRemoteAddress(client));
    try {
      // 读，关闭channel，并释放与channel相关联的一切资源
      client.close();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  protected static SocketAddress getRemoteAddress(AsynchronousSocketChannel client) {
    try {
      return client.getRemoteAddress();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
