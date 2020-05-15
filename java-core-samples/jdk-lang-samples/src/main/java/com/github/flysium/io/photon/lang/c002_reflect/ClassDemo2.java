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

public class ClassDemo2 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Class<Integer> c1 = int.class;// int 的类类型
		Class<String> c2 = String.class;// String类的类类型 String类字节码（自己发明的)
		Class<Double> c3 = double.class;
		Class<Double> c4 = Double.class;
		Class<Void> c5 = void.class;

		System.out.println(c1.getName());
		System.out.println(c2.getName());
		System.out.println(c2.getSimpleName());// 不包含包名的类的名称
		System.out.println(c5.getName());
	}

}
