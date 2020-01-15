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

public class ClassDemo1 {

	public static void main(String[] args) {
		// Foo的实例对象如何表示
		Foo foo1 = new Foo();// foo1就表示出来了.
		// Foo这个类 也是一个实例对象，Class类的实例对象,如何表示呢
		// 任何一个类都是Class的实例对象，这个实例对象有三种表示方式

		// 第一种表示方式--->实际在告诉我们任何一个类都有一个隐含的静态成员变量class
		Class<Foo> c1 = Foo.class;

		// 第二中表达方式 已经知道该类的对象通过getClass方法
		Class<? extends Foo> c2 = foo1.getClass();

		/*
		 * 官网 c1 ,c2 表示了Foo类的类类型(class type) 万事万物皆对象， 类也是对象，是Class类的实例对象
		 * 这个对象我们称为该类的类类型
		 *
		 */

		// 不管c1 or c2都代表了Foo类的类类型，一个类只可能是Class类的一个实例对象
		System.out.println(c1 == c2);

		// 第三种表达方式
		Class<?> c3 = null;
		try {
			c3 = Class.forName("com.imooc.reflect.Foo");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(c2 == c3);

		// 我们完全可以通过类的类类型创建该类的对象实例---->通过c1 or c2 or c3创建Foo的实例对象
		try {
			Foo foo = (Foo) c1.newInstance();// 需要有无参数的构造方法
			foo.print();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}
}

class Foo {

	void print() {
		System.out.println("foo");
	}
}