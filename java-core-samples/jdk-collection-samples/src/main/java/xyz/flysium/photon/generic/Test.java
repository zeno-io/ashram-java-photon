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

package xyz.flysium.photon.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sven Augustus
 */
public class Test {

	static class Species {

	}

	static class Human extends Species {

	}

	static class Man extends Human {

	}

	static class Woman extends Human {

	}

	public static void main(String[] args) {
		List<Human> list = new ArrayList<Human>();
		list.add(new Man());
		list.add(new Woman());
//		Man o11 = (Man) list.get(0); // 这不能保证转型成功，也不是泛型的初衷
		Human o12 = list.get(0);

		List<? extends Human> list2 = new ArrayList<Human>();
//		list2.add(new Object()); // 编译错误：这不能写入元素，类型校验失败
//		list2.add(new Species()); // 编译错误：这不能写入元素，类型校验失败
//		list2.add(new Human()); // 编译错误：这不能写入元素，类型校验失败
//		list2.add(new Man()); // 编译错误：这不能写入元素，类型校验失败
//		list2.add(new Woman()); // 编译错误：这不能写入元素，类型校验失败
//		Man o21 = (Man) list2.get(0);// 这不能保证转型成功，也不是泛型的初衷
		Human o22 = list2.get(0);

		List<? super Human> list3 = new ArrayList<Human>();
//		list3.add(new Object()); // 编译错误：这不能写入元素，类型校验失败
//		list3.add(new Species()); // 编译错误：这不能写入元素，类型校验失败
		list3.add(new Human());
		list3.add(new Man());
		list3.add(new Woman());
//		Man o31 = (Man) list3.get(0); // 这不能保证转型成功，也不是泛型的初衷
//		Human o32 = list3.get(0); // 编译错误：无法自动转型为 Number
		Object o33 = list3.get(0);

	}

}
