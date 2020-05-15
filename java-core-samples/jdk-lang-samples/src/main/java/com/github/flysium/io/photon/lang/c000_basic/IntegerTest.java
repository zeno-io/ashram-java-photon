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
 * Integer整数测试
 *
 * @author Sven Augustus
 * @version 2016年12月20日
 */
public class IntegerTest {

	public static void main(String[] args) {
		/**
		 * 当我们给一个Integer对象赋一个int值的时候，会调用Integer类的静态方法valueOf，如果看看valueOf的源代码就知道发生了什么。
		 *
		 * public static Integer valueOf(int i) { if (i >= IntegerCache.low && i
		 * <= IntegerCache.high) return IntegerCache.cache[i +
		 * (-IntegerCache.low)]; return new Integer(i); }
		 *
		 * 简单的说，如果整型字面量的值在-128到127之间， 那么不会new新的Integer对象，而是直接引用常量池中的Integer对象，
		 * 所以上面f1==f2的结果是true，而f3==f4的结果是false。
		 */
		Integer a = new Integer(3);
		Integer b = 3; // 将3自动装箱成Integer类型
		int c = 3;
		System.out.println(a == b); // false 两个引用没有引用同一对象
		System.out.println(a == c); // true a自动拆箱成int类型再和c比较

		Integer f1 = 100, f2 = 100;
		Integer f3 = 150, f4 = 150;

		System.out.println(f1 == f2);///// true
		System.out.println(f3 == f4);///// false
		/**
		 * toString int整数转x进制
		 */
		System.out.println(Integer.toString(36, 16));
		System.out.println(Integer.toString(361, 26).toUpperCase());
		/**
		 * parseInt与 decode x进制的数值转int整数
		 */
		System.out.println(Integer.parseInt("24", 16));
		System.out.println(Integer.decode("0x24"));
		/**
		 * lowestOneBit(int i) 保留最低位的1，同时将其他全部清零 highestOneBit(int i)
		 * 保留最高位的1，同时将低位全部清零 numberOfLeadingZeros(int i)
		 * 返回最高位的1之前0的个数。例如：1101000即104返回32-7=25 numberOfTrailingZeros(int i)
		 * 返回最低位的1之后0的个数。例如：1101000即104返回3
		 */
		System.out.println(Integer.toString(24, 2));
		System.out.println(Integer.toString(25, 2));
		test_highestOneBit(24);
		test_highestOneBit(25);
		test_lowestOneBit(24);
		test_lowestOneBit(25);
		System.out.println(Integer.numberOfLeadingZeros(25));// 27 = 32 - 5
		System.out.println(Integer.numberOfTrailingZeros(25));
		/**
		 * reverse。反转二进制补码中位的顺序。即将第32位的值与第1位的值互换，第31位的值与第2位的值互换，等等，依次
		 * reverseBytes:将第一个字节与第四个字节的位置互换，第二个字节与第三个字节位置互换*
		 */
		test(7, new Fucntion() {

			@Override
			public int invoke(int i) {
				int r = Integer.reverse(i);
				System.out.print("Integer.reverse(" + i + ")= [" + r);
				return r;
			}
		});
		test(7, new Fucntion() {

			@Override
			public int invoke(int i) {
				int r = Integer.reverseBytes(i);
				System.out.print("Integer.reverseBytes(" + i + ")= [" + r);
				return r;
			}
		});
		/**
		 * rotateLeft。将i左移distance,如果distance为负，则右移-distance
		 * rotateRight。将i无符号右移distance,如果distance为负，则左移-distance。负的肯定会移成正的。
		 */
		test(7, new Fucntion() {

			@Override
			public int invoke(int i) {
				int r = Integer.rotateLeft(i, 1);
				System.out.print("Integer.rotateLeft(" + i + ")= [" + r);
				return r;
			}
		});
		test(7, new Fucntion() {

			@Override
			public int invoke(int i) {
				int r = Integer.rotateRight(i, 1);
				System.out.print("Integer.rotateLeft(" + i + ")= [" + r);
				return r;
			}
		});
		test(8, new Fucntion() {

			@Override
			public int invoke(int i) {
				int r = Integer.rotateRight(i, 1);
				System.out.print("Integer.rotateLeft(" + i + ")= [" + r);
				return r;
			}
		});
		System.out.println(Integer.bitCount(25));// 此函数有时用于人口普查。
	}

	public static void test_highestOneBit(int i) {
		test(i, new Fucntion() {

			@Override
			public int invoke(int i) {
				int r = Integer.highestOneBit(i);
				System.out.print("Integer.highestOneBit(" + i + ")= [" + r);
				return r;
			}
		});
	}

	public static void test_lowestOneBit(int i) {
		test(i, new Fucntion() {

			@Override
			public int invoke(int i) {
				int r = Integer.lowestOneBit(i);
				System.out.print("Integer.lowestOneBit(" + i + ")= [" + r);
				return r;
			}
		});
	}

	public static void test(int i, Fucntion fn) {
		int result = fn.invoke(i);
		System.out
				.println("] ,input=" + toBinary32String(i) + ",output=" + toBinary32String(result));
	}

	public static String toBinary32String(int i) {
		String s = Integer.toBinaryString(i);
		if (s.length() >= 32) {
			return s;
		}
		StringBuffer sbf = new StringBuffer();
		for (int j = 0; j < 32 - s.length(); j++) {
			sbf.append('0');
		}
		return sbf.append(s).toString();
	}

	interface Fucntion {

		int invoke(int i);

	}

}
