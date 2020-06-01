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
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * SocketChannel示例
 *
 * @author Sven Augustus
 */
@SuppressWarnings("unused")
public class SocketChannelTest {

  public static void main(String[] args)
      throws IOException, InterruptedException, ClassNotFoundException {
    try (SocketChannel channel = SocketChannel.open()) {
      // 打开SocketChannel
      // 设置非阻塞模式，read的时候就不再阻塞
      channel.configureBlocking(false);
      // tcp连接网络
      channel.connect(new InetSocketAddress("127.0.0.1", 9595));
      // 连接服务器成功
      if (channel.finishConnect()) {
        // 往服务端写数据
        String serializable = "您好，ServerSocketChannel。";
        System.out.println("准备写：" + serializable);
        ByteBuffer byteBuffer = ByteBufferUtils.writeObject(serializable);
        channel.write(byteBuffer);
        System.out.println("准备读：");
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
      } else {
        System.out.println("连接失败，服务器拒绝服务");
      }
    }
  }

}
