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
