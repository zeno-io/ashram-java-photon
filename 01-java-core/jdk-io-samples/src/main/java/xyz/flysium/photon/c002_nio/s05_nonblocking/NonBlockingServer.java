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

package xyz.flysium.photon.c002_nio.s05_nonblocking;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * non-blocking I/O Server
 *
 * @author Sven Augustus
 */
public class NonBlockingServer {

  public static void main(String[] args) throws Exception {
    NonBlockingServer nioServer = new NonBlockingServer(9090);
    nioServer.start();
  }

  protected static final Logger logger = LoggerFactory.getLogger(NonBlockingServer.class);

  protected final int port;
  protected final int backlog;

  protected ServerSocketChannel server = null;

  public NonBlockingServer(int port) {
    this(port, 50);
  }

  public NonBlockingServer(int port, int backlog) {
    this.port = port;
    this.backlog = backlog;
  }

  @Override
  public String toString() {
    return "NonBlockingServer{" + "port=" + port
        + ", backlog=" + backlog
        + '}';
  }

  public void start() {
    try {
      initServer();
      accept();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    } finally {
      closeServer();
    }
  }

  /**
   * 涉及的系统调用：
   * <pre>
   * socket(PF_INET, SOCK_STREAM, IPPROTO_IP) = 4
   * fcntl(4, F_SETFL, O_RDWR|O_NONBLOCK)    = 0   //server.configureBlocking(false);
   * bind(4, {sa_family=AF_INET, sin_port=htons(9090)
   * listen(4, 50)
   * </pre>
   */
  protected void initServer() throws IOException {
    // 打开 ServerSocketChannel
    server = ServerSocketChannel.open();
    // 设置非阻塞模式，accept 的时候就不再阻塞
    server.configureBlocking(false);
    // 设置TCP Socket参数
    setServerSocketOptions(server);
    // 将 ServerSocket 绑定到特定地址（IP 地址和端口号）
    server.bind(new InetSocketAddress(port), backlog);
  }

  /**
   * 涉及的系统调用：
   * <pre>
   *    accept(8, {sa_family=AF_INET6, sin6_port=htons(33422), sin6_flowinfo=htonl(0), inet_pton(AF_INET6, "::ffff:127.0.0.1", &sin6_addr), sin6_scope_id=0}, [28]) = 10
   *    accept(8, 0x7f56f197e710, [28])         = -1 EAGAIN (资源暂时不可用)
   *
   *    read(10, "111\n", 8092)                 = 4
   *    read(10, 0x7f56ec399470, 8092)          = -1 EAGAIN (资源暂时不可用)
   * </pre>
   */
  protected void accept() throws IOException {
    for (; ; ) {
      // 1）非阻塞 accept 新的客户端连接
      SocketChannel client = server.accept();
      // 没有新的客户端连接
      if (client == null) {
        // nothing
      } else {
        logger.info("accept new client：" + getRemoteAddress(client));

        // 设置非阻塞模式，设置TCP Socket参数
        accepted(client);
      }
      // 2）循环遍历客户端连接，逐个尝试读写
      readyRead(client);
    }
  }

  protected void accepted(SocketChannel client) throws IOException {
    // 设置非阻塞模式
    client.configureBlocking(false);
    // 设置TCP Socket参数
    setClientSocketOptions(client);
  }

  /**
   * 设置 TCP Socket 参数
   */
  protected void setServerSocketOptions(ServerSocketChannel server) throws IOException {
    // server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
  }

  /**
   * 设置 TCP Socket 参数
   */
  protected void setClientSocketOptions(SocketChannel client) throws IOException {
    client.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
    client.setOption(StandardSocketOptions.TCP_NODELAY, true);
    client.setOption(StandardSocketOptions.SO_LINGER, 100);
  }

  private final ConcurrentMap<SocketChannel, Object> clientChannels = new ConcurrentHashMap<>();

  protected void readyRead(SocketChannel newClient) {
    if (newClient != null) {
      clientChannels.putIfAbsent(newClient, newClient);
    }

    // FIXME NOTICE: 不断 recv 系统调用
    clientChannels.keySet().forEach(channel -> doRead(channel, true, true));
  }

  protected void doRead(SocketChannel client, final boolean stopIfReadiedAnything,
      final boolean stopIfReadiedNothing) {
    ByteBuffer buffer = ByteBuffer.allocate(8092);
    buffer.clear();
    doRead(client, buffer,
        (c, buf, readCount) -> {
          // 转换，并打印请求内容
          String request = null;
          try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            baos.write(buffer.array(), 0, readCount);
            request = baos.toString();
          }
          logger.debug("readied from client: " + getRemoteAddress(client)
              + ", count: " + readCount + ", data: " + request);

          // FIXME 模拟业务逻辑处理时间耗时
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
          }
          byte[] bytes = ("recv->" + request).getBytes();

          // 写入响应内容
          buffer.clear();
          buffer.put(bytes);

          // FIXME NOTICE: 暂时没处理Send-Q满的情况，一直执行，占用 CPU 100%
          this.doWrite(client, buffer, null, null);

          return stopIfReadiedAnything;// break;
        }, (c, buf, readCount) -> {
          // FIXME NOTICE: 在 Non-blocking I/O 模型下，stopIfReadiedNothing=false, recv 读取为空, 不断 recv 系统调用, CPU空转
          logger.debug("readied nothing from client: " + getRemoteAddress(client)
              + " ! ");
          return stopIfReadiedNothing;
        });
  }

  protected void doRead(SocketChannel client, ByteBuffer buffer,
      ReadiedThenFunction readiedThenFunction,
      ReadiedThenFunction readiedIfRecvQueueEmptyFunction) {
    logger.debug("ready read from client: " + getRemoteAddress(client));
    buffer.clear();
    int readCount = 0;
    try {
      for (; ; ) {
        readCount = client.read(buffer);
        if (readCount > 0) {
          // 处理读取数据后续处理
          if (readiedThenFunction != null) {
            boolean breakOrNot = readiedThenFunction.breakIf(client, buffer, readCount);
            if (breakOrNot) {
              break;
            }
          }
        } else if (readCount == 0) {
          // 处理读取 Recv-Q 为空的情况
          if (readiedIfRecvQueueEmptyFunction != null) {
            boolean breakOrNot = readiedIfRecvQueueEmptyFunction.breakIf(client, buffer, 0);
            if (breakOrNot) {
              break;
            }
          }
        } else {
          logger.warn("readied -1 from client: " + getRemoteAddress(client)
              + " ! ");
          closeClient(client);
          break;
        }
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  protected void doWrite(SocketChannel client, ByteBuffer buffer,
      WrittenThenFunction writtenThenFunction,
      WrittenIfSendQueueFullFunction writtenIfSendQueueFullFunction) {
    logger.debug("ready write to client: " + getRemoteAddress(client));
    try {
      buffer.flip();
      if (buffer.hasRemaining()) {
        boolean returnOrNot = false;

        while (buffer.hasRemaining()) {
          int writeCount = client.write(buffer);
          if (writeCount < 0) {
            throw new EOFException();
          }
          // 处理 Send-Q 满了，暂时无法写出的情况
          if (writeCount == 0) {
            if (writtenIfSendQueueFullFunction != null) {
              returnOrNot = writtenIfSendQueueFullFunction.then(client, buffer, 0);
              if (returnOrNot) {
                break;
              }
            }
          }
        }
        buffer.clear();

        if (returnOrNot) {
          return;
        }
        // 处理写后续
        if (writtenThenFunction != null) {
          writtenThenFunction.apply(client, buffer);
        }
        logger.debug("write completed to client: " + getRemoteAddress(client));
      } else {
        logger.debug("write nothing to client: " + getRemoteAddress(client));
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  protected void closeServer() {
    logger.warn("close server.....");
    try {
      server.close();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  protected void closeClient(SocketChannel client) {
    logger.warn("close client：" + getRemoteAddress(client));
    try {
      client.close();
      clientChannels.remove(client);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  protected static SocketAddress getRemoteAddress(SocketChannel client) {
    try {
      return client.getRemoteAddress();
    } catch (IOException ignore) {
      // ignore
    }
    return null;
  }

  protected static ThreadPoolExecutor defaultThreadPoolExecutor(int threadQueueCapacity) {
    int coreMinSize = Runtime.getRuntime().availableProcessors() / 2;
    int coreMaxSize = Math.max(coreMinSize, 8);
    return new ThreadPoolExecutor(coreMinSize, coreMaxSize,
        60, TimeUnit.SECONDS,
        threadQueueCapacity <= 0 ? new SynchronousQueue<>()
            : new LinkedBlockingQueue<Runnable>(threadQueueCapacity),
        new ThreadPoolExecutor.CallerRunsPolicy());
  }

  @FunctionalInterface
  public interface ReadiedThenFunction {

    /**
     * after readied
     *
     * @param client    the client
     * @param buffer    the buffer
     * @param readCount the readied count
     * @return break the loop  or not
     * @throws IOException any I/O Exception
     */
    boolean breakIf(SocketChannel client, ByteBuffer buffer, int readCount) throws IOException;
  }

  @FunctionalInterface
  public interface WrittenThenFunction {

    /**
     * after written
     *
     * @param client the client
     * @param buffer the buffer
     * @throws IOException any I/O Exception
     */
    void apply(SocketChannel client, ByteBuffer buffer) throws IOException;
  }

  @FunctionalInterface
  public interface WrittenIfSendQueueFullFunction {

    /**
     * when Send-Q full
     *
     * @param client     the client
     * @param buffer     the buffer
     * @param writeCount the written count
     * @return return or not
     * @throws IOException any I/O Exception
     */
    boolean then(SocketChannel client, ByteBuffer buffer, int writeCount) throws IOException;
  }

}
