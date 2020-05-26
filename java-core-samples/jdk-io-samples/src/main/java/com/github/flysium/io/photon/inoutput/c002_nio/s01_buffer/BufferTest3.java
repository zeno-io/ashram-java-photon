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

import java.nio.CharBuffer;

/**
 * Buffer测试 - 标记和重置（mark 与 reset）
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class BufferTest3 {

	public static void main(String[] args) {
		CharBuffer charBuffer = CharBuffer.allocate(100);
		charBuffer.clear();
		charBuffer.put('1');
		charBuffer.put('2');
		charBuffer.put('3');
		charBuffer.put('4');
		charBuffer.put('5');
		charBuffer.mark();
		System.out.println(
				"position=" + charBuffer.position() + ",limit=" + charBuffer.limit() + ",remaining="
						+ charBuffer.remaining() + ",capacity=" + charBuffer.capacity());
		charBuffer.put('6');
		charBuffer.put('7');
		charBuffer.put('8');
		charBuffer.put('9');
		charBuffer.put('z');
		charBuffer.reset();
		System.out.println(
				"position=" + charBuffer.position() + ",limit=" + charBuffer.limit() + ",remaining="
						+ charBuffer.remaining() + ",capacity=" + charBuffer.capacity());
	}

}
