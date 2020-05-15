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
