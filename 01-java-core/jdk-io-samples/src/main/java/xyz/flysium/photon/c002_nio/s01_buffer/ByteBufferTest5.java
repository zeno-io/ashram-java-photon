/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c002_nio.s01_buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * ByteBuffer - order
 * <p>
 * 字节序，又称端序，尾序，英文：Endianness。 在计算机科学领域中，字节序是指存放多字节数据的字节（byte）的顺序， 典型的情况是整数在内存中的存放方式和网络传输的传输顺序。
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
