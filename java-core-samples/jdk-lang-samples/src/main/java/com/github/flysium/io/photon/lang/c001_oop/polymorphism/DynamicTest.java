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

package com.github.flysium.io.photon.lang.c001_oop.polymorphism;

/**
 * 运行时多态
 *
 * @author Sven Augustus
 */
public class DynamicTest {

	static abstract class Human {

		public void sayHello() {
			System.out.println("你好");
		}
	}

	static class Man extends DynamicTest.Human {

		public void sayHello() {
			System.out.println("您好，我是Y先生");
		}
	}

	static class Woman extends DynamicTest.Human {

		public void sayHello() {
			System.out.println("您好，我是X美女");
		}
	}

	public static void main(String[] args) {
		DynamicTest.Human manAsGuy = new DynamicTest.Man();// 注释1
		DynamicTest.Human womanAsGuy = new DynamicTest.Woman();
		manAsGuy.sayHello();
		womanAsGuy.sayHello();
	}

}
