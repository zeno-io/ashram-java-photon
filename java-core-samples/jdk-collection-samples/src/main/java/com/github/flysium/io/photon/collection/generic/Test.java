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

package com.github.flysium.io.photon.collection.generic;

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
