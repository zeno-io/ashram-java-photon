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

public class MethodDemo1 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// 要获取print(int ,int )方法 1.要获取一个方法就是获取类的信息，获取类的信息首先要获取类的类类型
		A a1 = new A();
		Class<? extends A> c = a1.getClass();
		/*
		 * 2.获取方法 名称和参数列表来决定 getMethod获取的是public的方法 getDelcaredMethod自己声明的方法
		 */
		try {
			// Method m = c.getMethod("print", new
			// Class[]{int.class,int.class});
			Method m = c.getMethod("print", int.class, int.class);

			// 方法的反射操作
			// a1.print(10, 20);方法的反射操作是用m对象来进行方法调用 和a1.print调用的效果完全相同
			// 方法如果没有返回值返回null,有返回值返回具体的返回值
			// Object o = m.invoke(a1,new Object[]{10,20});
			Object o = m.invoke(a1, 10, 20);
			System.out.println("==================");
			// 获取方法print(String,String)
			Method m1 = c.getMethod("print", String.class, String.class);
			// 用方法进行反射操作
			// a1.print("hello", "WORLD");
			o = m1.invoke(a1, "hello", "WORLD");
			System.out.println("===================");
			// Method m2 = c.getMethod("print", new Class[]{});
			Method m2 = c.getMethod("print");
			// m2.invoke(a1, new Object[]{});
			m2.invoke(a1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

class A {

	public void print() {
		System.out.println("helloworld");
	}

	public void print(int a, int b) {
		System.out.println(a + b);
	}

	public void print(String a, String b) {
		System.out.println(a.toUpperCase() + "," + b.toLowerCase());
	}
}
