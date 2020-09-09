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

package xyz.flysium.photon.c000_bio.s08_socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Blocking I/O Server
 *
 * @author Sven Augustus
 */
public class SocketServer {

  public static void main(String[] args) throws IOException {
    SocketServer server = new SocketServer(9090);
    server.start();
  }

  protected static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
  protected final int port;
  protected final int backlog;

  private ServerSocket server;

  public SocketServer(int port) {
    this(port, 50);
  }

  public SocketServer(int port, int backlog) {
    this.port = port;
    this.backlog = backlog;
  }

  @Override
  public String toString() {
    return "SocketServer{" + "port=" + port
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
   *   socket=fd3
   *   bind(fd3,9090)
   *   listen(fd3)
   * </pre>
   */
  private void initServer() throws IOException {
    server = new ServerSocket();
    // 设置 TCP Socket 参数
    setServerSocketOptions(server);
    // 将 ServerSocket 绑定到特定地址（IP 地址和端口号）
    server.bind(new InetSocketAddress(port), backlog);
  }

  /**
   * 涉及的系统调用：
   * <pre>
   *   accept(fd3,--》fd5  /blocking
   *
   *   recv(fd5--> /blocking  或 recvfrom(fd5--> /blocking
   * </pre>
   */
  private void accept() throws IOException {
    for (; ; ) {
      Socket client = null;
      try {
        client = server.accept();
      } catch (SocketTimeoutException ignore) {
        // ignore
      }
      if (client == null) {
        continue;
      }
      logger.info("accept new client：" + getRemoteAddress(client));

      // 设置 TCP Socket 参数
      setClientSocketOptions(client);

      readyRead(client);
    }
  }

  /**
   * 设置 TCP Socket 参数
   */
  protected void setServerSocketOptions(ServerSocket server) throws SocketException {
    server.setReuseAddress(true);
    // FIXME 当设置 accept 超时时间，JDK 中 其实改为 POLL，不再是Blocking I/O，
    //  系统调用：poll([{fd=11, events=POLLIN|POLLERR}], 1, 5) = 0 (Timeout)
//    server.setSoTimeout(5);
  }

  /**
   * 设置 TCP Socket 参数
   */
  protected void setClientSocketOptions(Socket client) throws SocketException {
    client.setKeepAlive(true);
    client.setTcpNoDelay(true);
    client.setSoLinger(true, 100);
    // FIXME 当设置 accept 超时时间，JDK 中 其实改为 POLL，不再是Blocking I/O，
    //  系统调用：poll([{fd=11, events=POLLIN|POLLERR}], 1, 5) = 0 (Timeout)
//    client.setSoTimeout(5);
  }

  protected void readyRead(Socket client) {
    doRead(client);
  }

  protected void doRead(Socket client) {
    logger.debug("ready read from client: " + getRemoteAddress(client));
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
      char[] cbuf = new char[8092];
      for (; ; ) {
        int readCount = 0;
        try {
          readCount = br.read(cbuf);
        } catch (SocketTimeoutException ignore) {
          // ignore
        }
        if (readCount > 0) {
          String request = new String(cbuf, 0, readCount);
          logger.debug("readied from client: " + getRemoteAddress(client)
              + ", count: " + readCount + ", data: " + request);

          // FIXME 模拟业务逻辑处理时间耗时
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
          }
          String response = "recv->" + request;

          // 返回响应内容
          doWriteString(client, response);
        } else if (readCount == 0) {
          logger.debug("readied nothing from client: " + getRemoteAddress(client)
              + " ! ");
          break;
        } else {
          logger.warn("readied -1 from client: " + getRemoteAddress(client)
              + " ! ");
          closeClient(client);
          break;
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  protected void doWriteString(Socket client, String response) throws IOException {
    logger.debug("ready write to client: " + getRemoteAddress(client));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    bw.write(response);
    bw.flush();
    logger.debug("write completed to client: " + getRemoteAddress(client));
  }

  protected void closeServer() {
    logger.warn("close server.....");
    try {
      server.close();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  protected static void closeClient(Socket client) {
    logger.warn("close client：" + getRemoteAddress(client));
    try {
      client.close();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  protected static SocketAddress getRemoteAddress(Socket client) {
    return client.getRemoteSocketAddress();
  }

}
