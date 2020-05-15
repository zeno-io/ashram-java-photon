package com.github.flysium.io.photon.inoutput.c002_nio.s05_selector;

import com.github.flysium.io.photon.inoutput.c002_nio.ByteBufferUtils;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * SocketChannel示例，使用Selector模式
 *
 * @author Sven Augustus
 */
@SuppressWarnings("unused")
public class SocketChannelTest2 {

	public static void main(String[] args)
			throws IOException, InterruptedException, ClassNotFoundException {
		new Thread(new ClientRunnable("A")).start();
		new Thread(new ClientRunnable("B")).start();
		new Thread(new ClientRunnable("C")).start();
		new Thread(new ClientRunnable("D")).start();
	}

	private static class ClientRunnable implements Runnable {

		private final String name;

		private ClientRunnable(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			SocketChannel channel = null;
			Selector selector = null;
			try {
				// 打开SocketChannel
				channel = SocketChannel.open();
				// 设置非阻塞模式，read的时候就不再阻塞
				channel.configureBlocking(false);
				// tcp连接网络
				channel.connect(new InetSocketAddress("127.0.0.1", 9595));

				// 创建Selector选择器
				selector = Selector.open();
				// 注册事件，监听读/写操作
				channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

				final int timeout = 1000;// 超时timeout毫秒
				if (channel.finishConnect()) {// 连接服务器成功
					while (true) {
						if (selector.select(timeout) == 0) {
							continue;
						}
						Set selectedKeys = selector.selectedKeys();
						Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
						while (keyIterator.hasNext()) {
							SelectionKey key = keyIterator.next();
							keyIterator.remove();
							if (key.isValid() && key.isWritable()) {// 写就绪，channel通道等待写数据。
								TimeUnit.SECONDS.sleep(3);
								SocketChannel socketChannel = (SocketChannel) key.channel();
								// 往服务端写数据
								String serializable = String.valueOf(new Random().nextInt(1000));
								// System.out.println("准备写：" + serializable);
								ByteBuffer byteBuffer = ByteBufferUtils.writeObject(serializable);
								socketChannel.write(byteBuffer);
								// 附加参数
								key.attach(serializable);
								// 切换读操作 , 以进行下一次的接口请求，即下一次读操作
								key.interestOps(SelectionKey.OP_READ);
							} else if (key.isReadable()) {// 读就绪，channel通道中有数据可读。
								SocketChannel socketChannel = (SocketChannel) key.channel();
								// System.out.println("准备读：");
								// 读取服务端发送的数据
								ByteBuffer buffer = ByteBuffer.allocate(1024);
								buffer.clear();
								int readBytes = socketChannel.read(buffer);
								if (readBytes >= 0) {// 非阻塞，立刻读取缓冲区可用字节
									Object object = ByteBufferUtils.readObject(buffer);
									// System.out.println(object);
									Integer integer = Integer
											.parseInt(String.valueOf(key.attachment()));
									System.out.println(
											"线程-" + name + "，请求服务器：" + integer + "，响应：" + object);
									// 切换写操作 , 以进行下一步的写操作，即接口请求
									key.interestOps(SelectionKey.OP_WRITE);
								} else if (readBytes < 0) { // 客户端连接已经关闭，释放资源
									System.out.println("服务端断开...");
								}
							}
						}
					}
				} else {
					System.out.println("连接失败，服务器拒绝服务");
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ClosedChannelException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				// 关闭SocketChannel
				if (channel != null) {
					try {
						channel.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
