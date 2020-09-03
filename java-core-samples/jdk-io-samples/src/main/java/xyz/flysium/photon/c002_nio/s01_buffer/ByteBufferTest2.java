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
 * ByteBuffer - slice 共享此缓冲区的子部分内容（位置从当前位置开始），互相可见，但位置、界限和标记值是独立的
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class ByteBufferTest2 {

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
		java.nio.ByteBuffer byteBuffer3 = byteBuffer1.slice();
		printBuffer(byteBuffer3, "byteBuffer1");

		byteBuffer1.position(1);
		byteBuffer1.put((byte) 5);
		byteBuffer1.position(4);
		byteBuffer1.put((byte) 7);
		/**
		 * 此时byteBuffer1 为 1 5 3 0 7
		 */
		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		// byteBuffer3.flip();
		// byteBuffer3.position(0);
		byteBuffer3.rewind();// 位置设置为 0 并丢弃标记
		while (byteBuffer3.hasRemaining()) {
			byte b = byteBuffer3.get();
			System.out.println(
					"byteBuffer3 position=" + byteBuffer3.position() + ",limit=" + byteBuffer3
							.limit()
							+ ",capacity=" + byteBuffer3.capacity() + ",data=" + b);
		}
		/**
		 * 注意array还是一样的
		 */
		if (byteBuffer3.hasArray()) {
			System.out.println("byteBuffer3 data=" + Arrays.toString(byteBuffer3.array()));
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
