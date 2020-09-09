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
 * &和&&的区别？
 *
 * @author Sven Augustus
 * @version 2016年10月30日
 */
public class OperTest {

	/**
	 * &运算符有两种用法：(1)按位与；(2)逻辑与。&&运算符是短路与运算。 逻辑与跟短路与的差别是非常巨大的，虽然二者都要求运算符左右两端的布尔值都是true整个表达式的值才是true。
	 * &&之所以称为短路运算是因为，如果&&左边的表达式的值是false，右边的表达式会被直接短路掉，不会进行运算。 很多时候我们可能都需要用&&而不是&，例如在验证用户登录时判定用户名不是null而且不是空字符串，
	 * 应当写为：username != null &&!username.equals("")，二者的顺序不能交换，更不能用&运算符，
	 * 因为第一个条件如果不成立，根本不能进行字符串的equals比较，否则会产生NullPointerException异常。 注意：逻辑或运算符（|）和短路或运算符（||）的差别也是如此。
	 */
	public static void main(String[] args) {
		System.out.println("test & 按位与：" + test(1));
		System.out.println("test & 按位与：" + test(11111));
		test1("1");
		try {
			test1(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		test2(null);
	}

	public static int test(int s) {
		return s & 102;
	}

	public static void test1(String s) {
		if (s.equals("1") & s != null) {
			System.out.println("test1 path 1");
		} else {
			System.out.println("test1 path 2");
		}
	}

	public static void test2(String s) {
		if (s != null && s.equals("1")) {
			System.out.println("test2 path 1");
		} else {
			System.out.println("test2 path 2");
		}
		/**
		 * 更优写法： if ( s != null && "1".equals(s)) 常量写在前面
		 */
	}

}
