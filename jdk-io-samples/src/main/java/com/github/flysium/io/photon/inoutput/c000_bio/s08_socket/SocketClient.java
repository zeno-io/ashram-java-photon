/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.inoutput.c000_bio.s08_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;

/**
 * web客户端模拟
 *
 * @author Sven Augustus
 * @version 2017年1月12日
 */
public class SocketClient {

	private static final int PORT = 9090;

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		// client("赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。十步杀一人，千里不留行。
		// 事了拂衣去，深藏身与名。闲过信陵饮，脱剑膝前横。将炙啖朱亥，持觞劝侯嬴。
		// 三杯吐然诺，五岳倒为轻。眼花耳热后，意气素霓生。救赵挥金锤，邯郸先震惊。千秋二壮士，烜赫大梁城。纵死侠骨香，不惭世上英。谁能书阁下，白首太玄经。" );
		for (int i = 0; i < 20; i++) {
			client("测试" + (i + 1));
		}
		while (Thread.activeCount() > 1) {
			Thread.sleep(10);
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start) + "ms");
	}

	public static void client(final String msg) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				java.net.Socket socket = null;
				try {
					socket = new Socket("127.0.0.1", PORT);

					PrintWriter pw = null;
					BufferedReader br = null;
					pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					/**
					 * Socket客户端发送数据到服务器
					 */
					pw.print(msg);
					pw.flush();
					try {
						socket.shutdownOutput();
					} catch (Exception e) {
						e.printStackTrace();
					}
					/**
					 * Socket客户端接收服务器的返回结果
					 */
					// String msg = br.readLine();
					StringBuffer stringBuffer = new StringBuffer();
					CharBuffer charBuffer = CharBuffer.allocate(1024);
					int numBytesRead = -1;
					while ((numBytesRead = br.read(charBuffer)) != -1) {
						if (numBytesRead == 0) {// 如果没有数据，则稍微等待一下
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							continue;
						}
						charBuffer.flip();
						stringBuffer.append(charBuffer.toString());
					}
					String msg = stringBuffer.toString();
					System.out.println("返回结果：" + msg);
					try {
						socket.shutdownInput();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (socket != null) {
							socket.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
