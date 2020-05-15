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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * 如何通过反射获取和设置对象私有字段的值？
 *
 * @author Sven Augustus
 * @version 2016年10月30日
 */
public class ReflectionUtil {

	/**
	 * 可以通过类对象的getDeclaredField()方法字段（Field）对象，
	 * 然后再通过字段对象的setAccessible(true)将其设置为可以访问， 接下来就可以通过get/set方法来获取/设置字段的值了。
	 *
	 * 下面的代码实现了一个反射的工具类，其中的两个静态方法分别用于获取和设置私有字段的值， 字段可以是基本类型也可以是对象类型且支持多级对象操作，
	 * 例如ReflectionUtil.get(dog, "owner.car.engine.id");可以获得dog对象的主人的汽车的引擎的ID号。
	 */
	public static void main(String[] args) {

		class A {

			private String ss;

			/*
			 * public String getSs() { return ss; }
			 *
			 * public void setSs(String ss) { this.ss = ss; }
			 */

			@Override
			public String toString() {
				return "A [ss=" + ss + "]";
			}
		}

		class B {

			private A a;

			/*
			 * public A getA() { return a; }
			 *
			 * public void setA(A a) { this.a = a; }
			 */

			@Override
			public String toString() {
				return "B [a=" + a + "]";
			}
		}

		B b = new B();
		ReflectionUtil.setValue(b, "a.ss", "string");
		System.out.println(b);
	}

	private ReflectionUtil() {
		throw new AssertionError();
	}

	/**
	 * 通过反射取对象指定字段(属性)的值
	 *
	 * @param target
	 *            目标对象
	 * @param fieldName
	 *            字段的名字
	 * @return 字段的值
	 */
	public static Object getValue(Object target, String fieldName) {
		Class<?> clazz = target.getClass();
		String[] fs = fieldName.split("\\.");

		try {
			for (int i = 0; i < fs.length - 1; i++) {
				Field f = clazz.getDeclaredField(fs[i]);
				f.setAccessible(true);
				target = f.get(target);
				clazz = target.getClass();
			}
			Field f = clazz.getDeclaredField(fs[fs.length - 1]);
			f.setAccessible(true);
			return f.get(target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过反射给对象的指定字段赋值
	 *
	 * @param target
	 *            目标对象
	 * @param fieldName
	 *            字段的名称
	 * @param value
	 *            值
	 */
	public static void setValue(Object target, String fieldName, Object value) {
		Class<?> clazz = target.getClass();
		String[] fs = fieldName.split("\\.");
		try {
			for (int i = 0; i < fs.length - 1; i++) {
				Field f = clazz.getDeclaredField(fs[i]);
				f.setAccessible(true);
				Object val = f.get(target);
				if (val == null) {
					Constructor<?> c = f.getType().getDeclaredConstructor();
					c.setAccessible(true);
					val = c.newInstance();
					f.set(target, val);
				}
				target = val;
				clazz = target.getClass();
			}
			Field f = clazz.getDeclaredField(fs[fs.length - 1]);
			f.setAccessible(true);
			f.set(target, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
