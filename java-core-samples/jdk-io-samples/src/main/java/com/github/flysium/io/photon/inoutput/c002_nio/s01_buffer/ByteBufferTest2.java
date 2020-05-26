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
