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

package com.github.flysium.io.photon.inoutput.c002_nio.s04_socket_channel;

import com.github.flysium.io.photon.inoutput.c002_nio.ByteBufferUtils;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;

/**
 * 网络UDP通道（DatagramChannel）测试 --作为客户端，发送数据
 *
 * @author Sven Augustus
 */
public class DatagramChannelClientTest {

  private static final int TIMES = 3;

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    try (DatagramChannel channel = DatagramChannel.open()) {
      // 打开DatagramChannel
      // 非阻塞模式
      channel.configureBlocking(false);
      // udp连接网络，发送数据将不用提供目的地址而且接收时的源地址也是已知的（这点类似SocketChannel），
      // 那么此时可以使用常规的read()和write()方法
      channel.connect(new InetSocketAddress("127.0.0.1", 9898));

      Serializable serializable = "您好，DatagramChannel。";
      System.out.println("准备写：" + serializable);
      ByteBuffer byteBuffer = ByteBufferUtils.writeObject(serializable);
      // 发送数据，以下为简单模拟非阻塞模式重发3次机制
      int bytesSent = 0;
      int sendTime = 1;
      while (bytesSent == 0 && sendTime <= TIMES) {
        // bytesSent = datagramChannel.send(byteBuffer, new
        // InetSocketAddress("127.0.0.1", 9898));
        bytesSent = channel.write(byteBuffer);
        sendTime++;
      }
      if (bytesSent > 0) {
        System.out.println("发送成功。");
      } else {
        System.out.println("发送失败。");
      }
      byteBuffer.clear();

      // 读取服务端发送的数据
      ByteBuffer buffer = ByteBuffer.allocate(1024);
      buffer.clear();
      int numBytesRead = -1;
      while ((numBytesRead = channel.read(buffer)) != -1) {
        // 如果没有数据，则稍微等待一下
        if (numBytesRead == 0) {
          try {
            TimeUnit.MILLISECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          continue;
        }
        Object object = ByteBufferUtils.readObject(buffer);
        System.out.println(object);
      }
    }
  }

}
