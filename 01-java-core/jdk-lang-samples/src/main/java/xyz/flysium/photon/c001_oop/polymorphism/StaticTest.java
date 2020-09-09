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

package xyz.flysium.photon.c001_oop.polymorphism;

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

