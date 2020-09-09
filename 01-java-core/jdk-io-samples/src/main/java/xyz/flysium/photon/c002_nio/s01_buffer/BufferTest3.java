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
