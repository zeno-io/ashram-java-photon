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
import java.net.InetAddress;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class UDPClient {

  public static void main(String[] args) throws IOException {
    System.out.println("------------ client start -->");
    DatagramSocket socket = new DatagramSocket();
    String text = "test";
    byte[] buf = text.getBytes();
    DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 9002);
    socket.send(packet);

    byte[] rspBuf = new byte[2048];
    DatagramPacket rspPacket = new DatagramPacket(rspBuf, rspBuf.length);
    socket.receive(rspPacket);
    System.out.println("响应：" + new String(rspPacket.getData()));

    socket.close();
  }

}
