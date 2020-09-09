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
