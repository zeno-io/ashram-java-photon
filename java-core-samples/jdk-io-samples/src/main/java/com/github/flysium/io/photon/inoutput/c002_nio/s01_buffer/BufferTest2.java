package com.github.flysium.io.photon.inoutput.c002_nio.s01_buffer;

import java.nio.ByteBuffer;

/**
 * Buffer测试 - compact
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class BufferTest2 {

	public static void main(String[] args) {
		java.nio.ByteBuffer byteBuffer = ByteBuffer.allocate(5);
		byteBuffer.put((byte) 1);
		byteBuffer.put((byte) 2);
		byteBuffer.put((byte) 3);
		byteBuffer.put((byte) 4);
		byteBuffer.put((byte) 5);
		byteBuffer.flip();// 切换读模式
		byte b = byteBuffer.get();
		System.out.println(
				"position=" + byteBuffer.position() + ",limit=" + byteBuffer.limit() + ",remaining="
						+ byteBuffer.remaining() + ",capacity=" + byteBuffer.capacity() + ",data="
						+ b);
		/**
		 * 不遗忘未读数据，切换写模式
		 */
		byteBuffer.compact();// 切换compact写模式
		System.out.println(
				"position=" + byteBuffer.position() + ",limit=" + byteBuffer.limit() + ",remaining="
						+ byteBuffer.remaining() + ",capacity=" + byteBuffer.capacity());
		byteBuffer.put((byte) 9);
		byteBuffer.flip();// 切换读模式
		while (byteBuffer.hasRemaining()) {
			b = byteBuffer.get();
			System.out.println("position=" + byteBuffer.position() + ",limit=" + byteBuffer.limit()
					+ ",remaining="
					+ byteBuffer.remaining() + ",capacity=" + byteBuffer.capacity() + ",data=" + b);
		}
	}

}
