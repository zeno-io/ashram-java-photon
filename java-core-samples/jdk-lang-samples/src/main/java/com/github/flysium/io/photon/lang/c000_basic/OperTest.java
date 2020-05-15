/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.lang.c000_basic;

/**
 * &和&&的区别？
 *
 * @author Sven Augustus
 * @version 2016年10月30日
 */
public class OperTest {

	/**
	 * &运算符有两种用法：(1)按位与；(2)逻辑与。&&运算符是短路与运算。
	 * 逻辑与跟短路与的差别是非常巨大的，虽然二者都要求运算符左右两端的布尔值都是true整个表达式的值才是true。
	 * &&之所以称为短路运算是因为，如果&&左边的表达式的值是false，右边的表达式会被直接短路掉，不会进行运算。
	 * 很多时候我们可能都需要用&&而不是&，例如在验证用户登录时判定用户名不是null而且不是空字符串， 应当写为：username != null
	 * &&!username.equals("")，二者的顺序不能交换，更不能用&运算符，
	 * 因为第一个条件如果不成立，根本不能进行字符串的equals比较，否则会产生NullPointerException异常。
	 * 注意：逻辑或运算符（|）和短路或运算符（||）的差别也是如此。
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
