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
