package com.github.flysium.io.photon.inoutput.c020_networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * client
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T03_Client_SocketOptions {

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

  public static void main(String[] args) throws IOException {
    Socket client = new Socket("127.0.0.1", 9090);
//    client.setKeepAlive(CLI_KEEPALIVE);
    client.setTcpNoDelay(CLI_TCP_NO_DELAY);
//    client.setSoLinger(CLI_SO_LINGER, CLI_SO_LINGER_N);
    client.setSoTimeout(CLI_SO_TIMEOUT);
    client.setSendBufferSize(CLI_SEND_BUF);
//    client.setReceiveBufferSize(CLI_RECV_BUF);
    client.setOOBInline(CLI_OOB);
//    client.setReuseAddress(CLI_REUSE_ADDR);

    OutputStream out = client.getOutputStream();

    System.out.println("-----------> send to server when you enter in console....");
    InputStream in = System.in;
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    while (true) {
      String line = reader.readLine();
      if (line != null) {
        byte[] bb = line.getBytes();
        for (byte b : bb) {
          out.write(b);
        }
      }
    }
  }

}
