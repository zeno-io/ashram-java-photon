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
import java.util.Arrays;

/**
 * ByteBuffer - warp 包装，将byte数组引用为缓存区数组，如果缓存区内容变更，byte数组也相应变更
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class ByteBufferTest3 {

	public static void main(String[] args) {
		byte[] bytes = new byte[]{1, 2, 3, 4, 5};
		java.nio.ByteBuffer byteBuffer1 = ByteBuffer.wrap(bytes);

		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		byteBuffer1.rewind();// 位置设置为 0 并丢弃标记
		byteBuffer1.put((byte) 9);
		if (byteBuffer1.hasArray()) {
			System.out.println("byteBuffer1 data=" + Arrays.toString(byteBuffer1.array()));
		}

		System.out.println(bytes[0]);
	}
}
