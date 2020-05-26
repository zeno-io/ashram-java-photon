/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.inoutput.c002_nio.s01_buffer;

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
