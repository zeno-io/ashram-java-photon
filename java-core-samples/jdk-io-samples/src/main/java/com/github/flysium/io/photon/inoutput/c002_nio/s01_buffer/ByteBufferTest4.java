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
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

/**
 * ByteBuffer - as视图
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class ByteBufferTest4 {

	public static void main(String[] args) {
		java.nio.ByteBuffer byteBuffer1 = ByteBuffer.allocate(31);
		printBuffer(byteBuffer1, "byteBuffer1");

		/**
		 * byteBuffer1的remaining>1 （除以2）
		 */
		CharBuffer charBuffer = byteBuffer1.asCharBuffer();
		printBuffer(charBuffer, "charBuffer");
		charBuffer.put('a');
		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		/**
		 * byteBuffer1的remaining>1 （除以2）
		 */
		ShortBuffer shortBuffer = byteBuffer1.asShortBuffer();
		printBuffer(shortBuffer, "shortBuffer");
		shortBuffer.put((short) 3);
		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		/**
		 * byteBuffer1的remaining>2 （除以4）
		 */
		IntBuffer intBuffer = byteBuffer1.asIntBuffer();
		printBuffer(intBuffer, "intBuffer");
		intBuffer.put(4);
		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		/**
		 * byteBuffer1的remaining>3 （除以8）
		 */
		LongBuffer longBuffer = byteBuffer1.asLongBuffer();
		printBuffer(longBuffer, "longBuffer");
		longBuffer.put(120);
		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		/**
		 * byteBuffer1的remaining>2 （除以4）
		 */
		FloatBuffer floatBuffer = byteBuffer1.asFloatBuffer();
		printBuffer(floatBuffer, "floatBuffer");
		floatBuffer.put(9f);
		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		/**
		 * byteBuffer1的remaining>3 （除以8）
		 */
		DoubleBuffer doubleBuffer = byteBuffer1.asDoubleBuffer();
		printBuffer(doubleBuffer, "doubleBuffer");
		doubleBuffer.put(1);
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
