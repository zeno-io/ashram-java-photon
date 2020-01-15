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
public class SocketServer_3_NIOThreadFix {

	private static final int PORT = 9090;

	public static void main(String[] args) {
		new Thread(new ServerHandler(PORT, 1000, 1024)).start();
	}

	/**
	 * NIO服务端线程
	 */
	public static class ServerHandler implements Runnable {

		private volatile boolean started;

		private Selector selector;// 多路复用选择器
		private java.nio.channels.ServerSocketChannel serverChannel;

		private final int timeout;
		private static final int ECHO_SERVER_TIMEOUT = 1000;

		private final int buffer_size;
		private static final int BUFFER_SIZE = 1024;

		/**
		 * @param port
		 *            指定要监听的端口号
		 */
		public ServerHandler(int port, int timeout, int buffer_size) {
			try {
				// 创建选择器
				selector = Selector.open();
				// 打开监听通道
				serverChannel = ServerSocketChannel.open();
				// 如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
				serverChannel.configureBlocking(false);// 开启非阻塞模式
				// 绑定端口 backlog设为1024
				serverChannel.socket().bind(new InetSocketAddress(PORT), 1024);
				// 监听客户端连接请求
				serverChannel.register(selector, SelectionKey.OP_ACCEPT);
				// 标记服务器已开启
				started = true;
				System.out.println("服务器已启动，端口号：" + port);
			} catch (IOException e) {
				e.printStackTrace();
				try {
					if (serverChannel != null) {
						serverChannel.close();
					}
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			} finally {
				this.timeout = timeout;
				this.buffer_size = buffer_size;
			}
		}

		public ServerHandler(int port) {
			this(port, ECHO_SERVER_TIMEOUT, BUFFER_SIZE);
		}

		public ServerHandler(int port, int timeout) {
			this(port, timeout, BUFFER_SIZE);
		}

		@Override
		public void run() {
			while (started) {// 循环遍历selector
				try {
					// 无论是否有读写事件发生，selector每隔timeout被唤醒一次
					if (selector.select(timeout) == 0) {
						continue;
					}
					Iterator<java.nio.channels.SelectionKey> it = selector.selectedKeys()
							.iterator();
					while (it.hasNext()) {
						SelectionKey key = (SelectionKey) it.next();
						it.remove();

						try {
							if (key.isAcceptable()) {// 处理新接入的请求消息
								SocketChannel channel = null;

								channel = ((ServerSocketChannel) key.channel()).accept();
								// System.out.println("监测到终端" + channel.socket().getInetAddress() + "端口" +
								// channel.socket().getPort());
								channel.configureBlocking(false); // 设置为非阻塞的
								channel.register(selector, SelectionKey.OP_READ); // 注册为读
							} else if (key.isReadable()) {// 读消息
								// key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));

								handleInput(key);
							}
						} catch (Exception e) {
							e.printStackTrace();
							if (key != null) {
								key.cancel();
								if (key.channel() != null) {
									key.channel().close();
								}
							}
						}
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			try {
				// selector关闭后会自动释放里面管理的资源
				if (selector != null) {
					selector.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void stop() {
			started = false;
		}

		private void handleInput(SelectionKey key) throws IOException, InterruptedException {
			SocketChannel channel = null;
			channel = (SocketChannel) key.channel();

			// 创建ByteBuffer，并开辟一个buffer_size的缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(buffer_size);
			// 读取请求码流，返回读取到的字节数
			int readBytes = channel.read(buffer);
			if (readBytes >= 0) {// 非阻塞，立刻读取缓冲区可用字节
				// 将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
				buffer.flip();
				/**
				 * 谨慎使用 如果客户端输入的长度超过 BUFFER_SIZE，
				 * 可能会出现java.nio.charset.MalformedInputException错误，原因是“半个中文问题”*
				 */
				CharBuffer charBuffer = CharsetHelper.decode(buffer);
				String msg = charBuffer.toString();
				System.out.println(
						"收到" + channel.socket().getInetAddress() + "端口" + channel.socket().getPort()
								+ "的消息：" + msg);
				/**
				 * 模拟业务逻辑处理时间耗时
				 */
				Thread.sleep(1000);

				channel.write(CharsetHelper.encode(CharBuffer.wrap("您好，已收到您发的消息[" + msg + "]")));
			} else if (readBytes < 0) { // 链路已经关闭，释放资源
				System.out.println(
						"客户端" + channel.socket().getInetAddress() + "端口" + channel.socket()
								.getPort() + "断开...");
				// key.cancel();
				channel.close();
			}
		}
	}

	static final class CharsetHelper {

		private static final String UTF_8 = "UTF-8";

		/**
		 * 不适合多线程环境，考虑使用ThreadLocal变量处理
		 */
		// private static CharsetEncoder encoder = Charset.forName(UTF_8).newEncoder();
		// private static CharsetDecoder decoder = Charset.forName(UTF_8).newDecoder();
		private CharsetHelper() {
		}

		/*
		 * public static ByteBuffer encode(CharBuffer in) throws
		 * CharacterCodingException { return encoder.encode(in); }
		 *
		 * public static CharBuffer decode(ByteBuffer in) throws
		 * CharacterCodingException { return decoder.decode(in); }
		 */

		private static ThreadLocal<CharsetEncoder> encoder = new ThreadLocal<CharsetEncoder>() {
			protected CharsetEncoder initialValue() {
				return null;
			}
		};
		private static ThreadLocal<CharsetDecoder> decoder = new ThreadLocal<CharsetDecoder>() {
			protected CharsetDecoder initialValue() {
				return null;
			}
		};

		public static ByteBuffer encode(CharBuffer charBuffer) throws CharacterCodingException {
			CharsetEncoder charsetEncoder = encoder.get();
			if (charsetEncoder == null) {
				charsetEncoder = Charset.forName(UTF_8).newEncoder();
				encoder.set(charsetEncoder);
			}
			return charsetEncoder.encode(charBuffer);
		}

		public static CharBuffer decode(ByteBuffer byteBuffer) throws CharacterCodingException {
			CharsetDecoder charsetDecoder = decoder.get();
			if (charsetDecoder == null) {
				charsetDecoder = Charset.forName(UTF_8).newDecoder();
				decoder.set(charsetDecoder);
			}
			return charsetDecoder.decode(byteBuffer);
		}
	}

}
