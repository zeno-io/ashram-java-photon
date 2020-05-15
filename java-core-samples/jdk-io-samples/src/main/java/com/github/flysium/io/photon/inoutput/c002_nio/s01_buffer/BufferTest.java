package com.github.flysium.io.photon.inoutput.c002_nio.s01_buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Buffer测试 - 传输数据（clear、put、flip、get）
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class BufferTest {

	public static void main(String[] args) {
		java.nio.ByteBuffer byteBuffer = ByteBuffer.allocate(5);
		/**
		 * 清空数据，切换写模式
		 */
		byteBuffer.clear();
		// byteBuffer.compact();//第一次未读不要调用compact()
		printBuffer(byteBuffer, null);
		byteBuffer.put((byte) 1);
		printBuffer(byteBuffer, null);
		byteBuffer.put((byte) 2);
		printBuffer(byteBuffer, null);
		byteBuffer.put((byte) 3);
		printBuffer(byteBuffer, null);
		byteBuffer.put((byte) 4);
		printBuffer(byteBuffer, null);
		byteBuffer.put((byte) 5);
		printBuffer(byteBuffer, null);
		try {
			byteBuffer.put((byte) 6);
			printBuffer(byteBuffer, null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		/**
		 * 切换读模式
		 */
		byteBuffer.flip();
		while (byteBuffer.hasRemaining()) {
			byte b = byteBuffer.get();
			printBufferData(byteBuffer, null, b);
		}
	}

	private static void printBuffer(Buffer buffer, String name) {
		System.out.println(
				(name != null && !name.isEmpty() ? name + " " : "") + "position=" + buffer
						.position()
						+ ",limit=" + buffer.limit() + ",remaining=" + buffer.remaining()
						+ ",capacity=" + buffer.capacity());
	}

	private static void printBufferData(Buffer buffer, String name, Object object) {
		System.out.println(
				(name != null && !name.isEmpty() ? name + " " : "") + "position=" + buffer
						.position()
						+ ",limit=" + buffer.limit() + ",remaining=" + buffer.remaining()
						+ ",capacity=" + buffer.capacity()
						+ ",data=" + object);
	}

}
