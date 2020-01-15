package com.github.flysium.io.photon.inoutput.c002_nio.s01_buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * ByteBuffer - order
 *
 * 字节序，又称端序，尾序，英文：Endianness。 在计算机科学领域中，字节序是指存放多字节数据的字节（byte）的顺序，
 * 典型的情况是整数在内存中的存放方式和网络传输的传输顺序。
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class ByteBufferTest5 {

	public static void main(String[] args) {
		String string = "abcde";

		java.nio.ByteBuffer byteBuffer1 = ByteBuffer.allocate(10);
		System.out.println(byteBuffer1.order());

		byteBuffer1.rewind();// 位置设置为 0 并丢弃标记
		byteBuffer1.order(ByteOrder.BIG_ENDIAN);
		byteBuffer1.asCharBuffer().put(string);
		System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));

		byteBuffer1.rewind();// 位置设置为 0 并丢弃标记
		byteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer1.asCharBuffer().put(string);
		System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));

		/**
		 * 无效用法1，只更改order，不重新填充数据，存储是不会改变的，只有下次才生效
		 */
		byteBuffer1.rewind();// 位置设置为 0 并丢弃标记
		byteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
		System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));

		/**
		 * 无效用法2，填充完数据再改order，存储是不会改变的，只有下次才生效
		 */
		byteBuffer1.rewind();// 位置设置为 0 并丢弃标记
		byteBuffer1.asCharBuffer().put(string);
		byteBuffer1.order(ByteOrder.LITTLE_ENDIAN);
		System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
	}
}
