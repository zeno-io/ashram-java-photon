package com.github.flysium.io.photon.inoutput.c002_nio.s01_buffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * ByteBuffer - warp 包装，将byte数组引用为缓存区数组，如果缓存区内容变更，byte数组也相应变更
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class ByteBufferTest3 {

	public static void main(String[] args) {
		byte[] bytes = new byte[]{1, 2, 3, 4, 5};
		java.nio.ByteBuffer byteBuffer1 = ByteBuffer.wrap(bytes);

		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		byteBuffer1.rewind();// 位置设置为 0 并丢弃标记
		byteBuffer1.put((byte) 9);
		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		System.out.println(bytes[0]);
	}
}
