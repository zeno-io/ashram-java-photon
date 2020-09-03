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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * ByteBuffer - duplicate 共享此缓冲区内容，互相可见，但位置、界限和标记值是独立的
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class ByteBufferTest {

	public static void main(String[] args) {
		java.nio.ByteBuffer byteBuffer1 = ByteBuffer.allocate(5);
		byteBuffer1.clear();

		byteBuffer1.put((byte) 1);
		byteBuffer1.put((byte) 2);
		byteBuffer1.put((byte) 3);
		/**
		 * 此时byteBuffer1 为 1 2 3 0 0
		 */
		printBuffer(byteBuffer1, "byteBuffer1");
		java.nio.ByteBuffer byteBuffer2 = byteBuffer1.duplicate();
		printBuffer(byteBuffer2, "byteBuffer2");

		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		// byteBuffer1.clear();
		byteBuffer1.position(0);
		byteBuffer1.put((byte) 4);
		/**
		 * 此时 共享区 为 4 2 3 0 0
		 */
		System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));

		// byteBuffer2.position(0);
		byteBuffer2.rewind();// 位置设置为 0 并丢弃标记
		while (byteBuffer2.hasRemaining()) {
			byte b = byteBuffer2.get();
			System.out.println(
					"byteBuffer2 position=" + byteBuffer2.position() + ",limit=" + byteBuffer2
							.limit()
							+ ",capacity=" + byteBuffer2.capacity() + ",data=" + b);
		}
		if (byteBuffer2.hasArray()) {
			System.out.println("byteBuffer2 data=" + Arrays.toString(byteBuffer2.array()));
		}

		byteBuffer2.position(1);
		byteBuffer2.put((byte) 8);
		/**
		 * 此时 共享区 为 4 8 3 0 0
		 */
		printBuffer(byteBuffer1, "byteBuffer1");
		printBuffer(byteBuffer2, "byteBuffer2");

		// byteBuffer1.flip();
		// byteBuffer1.position(0);
		byteBuffer1.rewind();// 位置设置为 0 并丢弃标记
		while (byteBuffer1.hasRemaining()) {
			byte b = byteBuffer1.get();
			System.out.println(
					"byteBuffer1 position=" + byteBuffer1.position() + ",limit=" + byteBuffer1
							.limit()
							+ ",capacity=" + byteBuffer1.capacity() + ",data=" + b);
		}
		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}
	}

	private static void printBuffer(Buffer buffer, String name) {
		System.out.println(
				(name != null && !name.isEmpty() ? name + " " : "") + "position=" + buffer
						.position()
						+ ",limit=" + buffer.limit() + ",remaining=" + buffer.remaining()
						+ ",capacity=" + buffer.capacity());
	}

}
