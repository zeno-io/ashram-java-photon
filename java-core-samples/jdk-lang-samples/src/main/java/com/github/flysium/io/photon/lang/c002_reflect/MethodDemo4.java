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

package com.github.flysium.io.photon.lang.c002_reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MethodDemo4 {

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void main(String[] args) {
		ArrayList list = new ArrayList();

		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("hello");
		// list1.add(20);错误的
		Class<? extends ArrayList> c1 = list.getClass();
		Class c2 = list1.getClass();
		System.out.println(c1 == c2);
		// 反射的操作都是编译之后的操作

		/*
		 * c1==c2结果返回true说明编译之后集合的泛型是去泛型化的 Java中集合的泛型，是防止错误输入的，只在编译阶段有效，
		 * 绕过编译就无效了 验证：我们可以通过方法的反射来操作，绕过编译
		 */
		try {
			Method m = c2.getMethod("add", Object.class);
			m.invoke(list1, 20);// 绕过编译操作就绕过了泛型
			System.out.println(list1.size());
			System.out.println(list1);
			/*
			 * for (String string : list1) { System.out.println(string); }
			 */// 现在不能这样遍历
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
