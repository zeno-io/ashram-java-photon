package com.github.flysium.io.photon.inoutput.c002_nio.s01_buffer;

import com.github.flysium.io.photon.inoutput.c002_nio.ByteBufferUtils;
import java.nio.ByteBuffer;

/**
 * Buffer测试 - byteBuffer
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class BufferTest5 {

	public static void main(String[] args) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(100);
		byteBuffer.clear();
		byteBuffer.put((byte) 1);
		byteBuffer.put((byte) 2);
		byteBuffer.put((byte) 3);
		System.out.println(
				"position=" + byteBuffer.position() + ",limit=" + byteBuffer.limit() + ",capacity="
						+ byteBuffer.capacity() + ",arrayOffset=" + byteBuffer.arrayOffset());
		byte[] chars = ByteBufferUtils.readToBytes(byteBuffer);

		for (int i = 0; i < chars.length; i++) {
			System.out.print(chars[i]);
		}
		System.out.println();
		System.out.println(
				"position=" + byteBuffer.position() + ",limit=" + byteBuffer.limit() + ",capacity="
						+ byteBuffer.capacity() + ",arrayOffset=" + byteBuffer.arrayOffset());
	}

}
