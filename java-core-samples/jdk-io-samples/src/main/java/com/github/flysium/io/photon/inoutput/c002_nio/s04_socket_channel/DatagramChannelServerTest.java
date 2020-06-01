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
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeUnit;

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
