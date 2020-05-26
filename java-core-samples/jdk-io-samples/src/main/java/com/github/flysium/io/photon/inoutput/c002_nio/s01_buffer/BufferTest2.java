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
