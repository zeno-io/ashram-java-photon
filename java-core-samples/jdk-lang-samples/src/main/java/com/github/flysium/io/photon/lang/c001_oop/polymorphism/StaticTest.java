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
 * 编译时多态
 *
 * @author Sven Augustus
 */
public class StaticTest {

	static abstract class Human {

	}

	static class Man extends StaticTest.Human {

	}

	static class Woman extends StaticTest.Human {

	}

	static class Girl extends StaticTest.Woman {

	}

	public void sayHello(Object guy) {
		System.out.println("你...");
	}

	public void sayHello(Human guy) {
		System.out.println("你好");
	}

	public void sayHello(Man guy) {
		System.out.println("您好，先生");
	}

	public void sayHello(Woman guy) {
		System.out.println("您好，美女");
	}

	public void sayHello(Girl guy) {
		System.out.println("您好，美少女");
	}

	public static void main(String[] args) {
		StaticTest test = new StaticTest();
		StaticTest.Human manAsGuy = new StaticTest.Man();
		StaticTest.Human womanAsGuy = new StaticTest.Woman();
		StaticTest.Woman girlAsWoman = new StaticTest.Girl();
		test.sayHello(manAsGuy);
		test.sayHello(womanAsGuy);
		test.sayHello(girlAsWoman);
	}

}

