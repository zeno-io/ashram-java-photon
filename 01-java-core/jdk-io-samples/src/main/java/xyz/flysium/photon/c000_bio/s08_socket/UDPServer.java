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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class UDPServer {

  public static void main(String[] args) throws IOException {
    System.out.println("------------ server start -->");
    DatagramSocket socket = new DatagramSocket(9002);
    while (true) {
      byte[] buf = new byte[2048];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      socket.receive(packet);
      byte[] data = packet.getData();
      String msg = new String(data, 0, packet.getLength());
      System.out.println("请求：" + msg);

      // 响应客户端的内容
      byte[] rspBuf = (msg + ", OK").getBytes();
      socket.send(new DatagramPacket(rspBuf, rspBuf.length, packet.getSocketAddress()));
    }
  }

}
