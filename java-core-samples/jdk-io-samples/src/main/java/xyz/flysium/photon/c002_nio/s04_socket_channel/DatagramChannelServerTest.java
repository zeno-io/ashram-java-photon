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
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;
import xyz.flysium.photon.c002_nio.ByteBufferUtils;

/**
 * 网络UDP通道（DatagramChannel）测试 --作为服务端
 *
 * @author Sven Augustus
 */
public class DatagramChannelServerTest {

	public static void main(String[] args)
			throws IOException, ClassNotFoundException, InterruptedException {
		try (DatagramChannel channel = DatagramChannel.open()) {
			// 打开DatagramChannel
			// 非阻塞模式
			channel.configureBlocking(false);
			// 将 UDP 绑定到特定地址（IP 地址和端口号），作为服务端监听端口
			channel.bind(new InetSocketAddress("127.0.0.1", 9898));

			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				buffer.clear();
				SocketAddress socketAddress = channel.receive(buffer);
				if (socketAddress == null) {
					// System.out.println("没有客户端连接");
					TimeUnit.MILLISECONDS.sleep(1);
					continue;
				}
				System.out.println("准备读：" + socketAddress);
				Serializable object = ByteBufferUtils.readObject(buffer);
				System.out.println(object);
				// 往客户端写数据
				String serializable = "您好，客户端" + socketAddress.toString();
				System.out.println("准备写：" + serializable);
				ByteBuffer byteBuffer = ByteBufferUtils.writeObject(serializable);
				channel.send(byteBuffer, socketAddress);
			}
		}
	}

}
