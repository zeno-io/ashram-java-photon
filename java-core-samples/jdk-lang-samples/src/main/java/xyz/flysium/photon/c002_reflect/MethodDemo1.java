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

package xyz.flysium.photon.c002_reflect;

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
