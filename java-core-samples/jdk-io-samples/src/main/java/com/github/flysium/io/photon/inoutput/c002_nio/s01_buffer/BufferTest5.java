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

import com.github.flysium.io.photon.inoutput.c002_nio.ByteBufferUtils;
import java.nio.ByteBuffer;

/**
 * Buffer测试 - byteBuffer
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class BufferTest5 {

	public static void main(String[] args) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(100);
		byteBuffer.clear();
		byteBuffer.put((byte) 1);
		byteBuffer.put((byte) 2);
		byteBuffer.put((byte) 3);
		System.out.println(
				"position=" + byteBuffer.position() + ",limit=" + byteBuffer.limit() + ",capacity="
						+ byteBuffer.capacity() + ",arrayOffset=" + byteBuffer.arrayOffset());
		byte[] chars = ByteBufferUtils.readToBytes(byteBuffer);

		for (int i = 0; i < chars.length; i++) {
			System.out.print(chars[i]);
		}
		System.out.println();
		System.out.println(
				"position=" + byteBuffer.position() + ",limit=" + byteBuffer.limit() + ",capacity="
						+ byteBuffer.capacity() + ",arrayOffset=" + byteBuffer.arrayOffset());
	}

}
