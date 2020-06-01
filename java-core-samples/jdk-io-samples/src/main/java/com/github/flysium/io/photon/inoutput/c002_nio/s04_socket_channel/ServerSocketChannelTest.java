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
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * ServerSocketChannel示例
 *
 * @author Sven Augustus
 */
public class ServerSocketChannelTest {

	public static void main(String[] args)
			throws IOException, InterruptedException, ClassNotFoundException {
		try (ServerSocketChannel channel = ServerSocketChannel.open()) {
			// 打开 ServerSocketChannel
			// 设置非阻塞模式，read的时候就不再阻塞
			channel.configureBlocking(false);
			// 将 ServerSocket 绑定到特定地址（IP 地址和端口号）
			channel.bind(new InetSocketAddress("127.0.0.1", 9595));

			while (true) {
				// 监听新进来的连接
				java.nio.channels.SocketChannel socketChannel = channel.accept();
				if (socketChannel == null) {
					// System.out.println("没有客户端连接");
					TimeUnit.SECONDS.sleep(1);
					continue;
				}
				System.out.println("准备读：");
				// 读取客户端发送的数据
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				buffer.clear();
				socketChannel.read(buffer);
				Object object = ByteBufferUtils.readObject(buffer);
				System.out.println(object);
				// 往客户端写数据
				String serializable = "您好，客户端" + socketChannel.getRemoteAddress();
				System.out.println("准备写：" + serializable);
				ByteBuffer byteBuffer = ByteBufferUtils.writeObject(serializable);
				socketChannel.write(byteBuffer);
			}
		}
	}

}
