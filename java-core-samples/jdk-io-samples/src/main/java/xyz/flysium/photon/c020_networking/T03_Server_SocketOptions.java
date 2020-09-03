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

package xyz.flysium.photon.c020_networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * server
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T03_Server_SocketOptions {

  // ServerSocket的TCP相关参数
  // backlog 配置ServerSocket的最大客户端等待队列大小。
  private static final int BACK_LOG = 2;
  // 表示是否允许重用ServerSocket所绑定的本地地址。
  private static final boolean REUSE_ADDR = false;
  // 表示接收数据的缓冲区的大小。
  private static final int RECEIVE_BUFFER = 10;
  // 表示ServerSocket的accept()方法等待客户连接的超时时间，以毫秒为单位
  private static final int SO_TIMEOUT = 0;

  // Socket的TCP相关参数
  // 表示对于长时间处于空闲状态的Socket，是否要自动把它关闭。
  private static final boolean CLI_KEEPALIVE = false;
  // 表示是否禁用nagle算法，立即发送数据。
  private static final boolean CLI_TCP_NO_DELAY = false;
  // 表示是否允许重用Socket所绑定的本地地址。
  private static final boolean CLI_REUSE_ADDR = false;
  // 表示接收数据时的等待超时时间，以毫秒为单位。
  private static final int CLI_SO_TIMEOUT = 0;
  // 表示当执行Socket的close()方法时，是否取消立即关闭底层的Socket。
  private static final boolean CLI_SO_LINGER = true;
  private static final int CLI_SO_LINGER_N = 0;
  // 表示发送数据的缓冲区的大小。
  private static final int CLI_SEND_BUF = 20;
  // 表示接收数据的缓冲区的大小。
  private static final int CLI_RECV_BUF = 20;
  // 表示是否支持发送一个字节的TCP紧急数据。
  private static final boolean CLI_OOB = false;

  public static void main(String[] args) throws IOException, InterruptedException {
    ServerSocket server = new ServerSocket();
    server.bind(new InetSocketAddress(9090), 2);
    server.bind(new InetSocketAddress(9090), BACK_LOG);
    server.setReuseAddress(REUSE_ADDR);
    server.setReceiveBufferSize(RECEIVE_BUFFER);
    server.setSoTimeout(SO_TIMEOUT);
    System.out.println("server start !");

    // 测试用
    System.out.println("-----------> start to accept clients when you enter in console....");
    System.in.read();

    // 开始从服务器内存中接受客户端连接
    while (true) {
      Socket client = server.accept();
      client.setKeepAlive(CLI_KEEPALIVE);
      client.setTcpNoDelay(CLI_TCP_NO_DELAY);
      client.setSoLinger(CLI_SO_LINGER, CLI_SO_LINGER_N);
      client.setSoTimeout(CLI_SO_TIMEOUT);
      client.setSendBufferSize(CLI_SEND_BUF);
      client.setReceiveBufferSize(CLI_RECV_BUF);
      client.setOOBInline(CLI_OOB);
      client.setReuseAddress(CLI_REUSE_ADDR);

      System.out.println("receive from " + client.getPort());

      InputStream in = client.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      char[] data = new char[1024];
      while (true) {

        int num = reader.read(data);

        if (num > 0) {
          System.out.println("client read some data is :" + num
              + " val :" + new String(data, 0, num));
        } else if (num == 0) {
          System.out.println("client readed nothing!");
          continue;
        } else {
          System.out.println("client readed -1...");

          System.out.println("-----------> close client when you enter in console....");
          System.in.read();
          client.close();
          break;
        }
      }
    }
  }

}
