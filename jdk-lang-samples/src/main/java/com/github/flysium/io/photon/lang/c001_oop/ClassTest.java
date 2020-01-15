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

package com.github.flysium.io.photon.lang.c001_oop;

/**
 * 创建对象时构造器的调用顺序
 *
 * @author Sven Augustus
 * @version 2016年10月30日
 */
public class ClassTest {

	/**
	 * 创建对象时构造器的调用顺序是：
	 *
	 * 先初始化静态成员，然后调用父类构造器，再初始化非静态成员，最后调用自身构造器。
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// A b2 = new B();
		B b = new B();
		A b2 = new B();
		A c = new C();
		// ab = new B();
	}

}

class A {

	/**
	 * f1 -- 在该类或子类第一次new的时候第一个执行
	 */
	static {
		System.out.print("A-static ");
	}

	/**
	 * 在该类new的时候执行，或子类默认new的时候先执行
	 */
	public A() {
		System.out.print("A-construction ");
	}
}

class B extends A {

	static {
		System.out.print("B-static ");
	}

	public B() {
		System.out.print("B-construction ");
	}
}

class C extends A {

	static {
		System.out.print("C-static ");
	}

	public C() {
		System.out.print("C-construction ");
	}
}