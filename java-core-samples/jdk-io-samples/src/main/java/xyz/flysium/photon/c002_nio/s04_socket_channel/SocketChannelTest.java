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

package xyz.flysium.photon.c002_nio.s04_socket_channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;
import xyz.flysium.photon.c002_nio.ByteBufferUtils;

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
