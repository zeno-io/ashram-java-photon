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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

/**
 * web服务器模拟---NIO实现方式
 *
 * @author Sven Augustus
 * @version 2017年1月12日
 */
public class SocketServer_3_NIOThread {

	private static final int PORT = 9090;
	private static final int ECHO_SERVER_TIMEOUT = 5000;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		/**
		 * NIO
		 */
		java.nio.channels.ServerSocketChannel serverSocketChannel = null;
		Selector selector = null;// 多路复用选择器
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
			/**
			 * 非阻塞方式
			 */
			serverSocketChannel.configureBlocking(false);
			selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			while (true) {
				try {
					if (selector.select(ECHO_SERVER_TIMEOUT) != 0) {
						Iterator<java.nio.channels.SelectionKey> it = selector.selectedKeys()
								.iterator();
						while (it.hasNext()) {
							SelectionKey selectionKey = (SelectionKey) it.next();
							it.remove();

							handleKey(selector, selectionKey);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocketChannel != null) {
					serverSocketChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void handleKey(Selector selector, SelectionKey selectionKey) throws IOException {
		SocketChannel socketChannel = null;
		try {
			if (selectionKey.isAcceptable()) {
				ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
				socketChannel = serverChannel.accept();
				System.out.println(
						"ServerSocket服务器接监测到一个终端" + socketChannel.socket().getInetAddress() + "端口"
								+ socketChannel.socket().getPort() + " 已连接");
				/**
				 * 非阻塞方式
				 */
				socketChannel.configureBlocking(false);
				socketChannel.register(selector, SelectionKey.OP_READ);
			} else if (selectionKey.isReadable()) {
				socketChannel = (SocketChannel) selectionKey.channel();

				ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
				if (socketChannel.read(byteBuffer) > 0) {
					// System.out.println("ServerSocket服务器接收到一个终端" +
					// socketChannel.socket().getInetAddress() + "信号...");
					byteBuffer.flip();
					CharBuffer charBuffer = CharsetHelper.decode(byteBuffer);
					String msg = charBuffer.toString();
					System.out
							.println("收到" + socketChannel.socket().getInetAddress() + "的消息：" + msg);

					/**
					 * 模拟业务逻辑处理时间耗时
					 */
					Thread.sleep(1000);

					socketChannel.write(CharsetHelper
							.encode(CharBuffer.wrap("您好，已收到您发的消息[" + msg + "]")));
				} else {
					if (socketChannel != null) {
						socketChannel.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (socketChannel != null) {
					socketChannel.close();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	static final class CharsetHelper {

		private static final String UTF_8 = "UTF-8";
		private static CharsetEncoder encoder = Charset.forName(UTF_8).newEncoder();
		private static CharsetDecoder decoder = Charset.forName(UTF_8).newDecoder();

		private CharsetHelper() {
		}

		public static ByteBuffer encode(CharBuffer in) throws CharacterCodingException {
			return encoder.encode(in);
		}

		public static CharBuffer decode(ByteBuffer in) throws CharacterCodingException {
			return decoder.decode(in);
		}
	}

}
