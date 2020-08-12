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

package com.github.flysium.io.photon.inoutput.c000_bio.s08_socket;

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
