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

package xyz.flysium.photon.c000_basic;

/**
 * Math类测试
 *
 * @author Sven Augustus
 * @version 2016年10月30日
 */
public class MathTest {

	public static void main(String[] args) {
		/**
		 * Math.round(11.5)的返回值是12，Math.round(-11.5)的返回值是-11。
		 * 四舍五入的原理是在参数上加0.5然后进行下取整。
		 */
		System.out.println(Math.round(11.5));
		System.out.println(Math.round(-11.5));

		/**
		 * 左移3位相当于乘以2的3次方，右移3位相当于除以2的3次方
		 */
		System.out.println(5 << 3);
		System.out.println(80 >> 3);

	}
}
