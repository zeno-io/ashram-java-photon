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

package com.github.flysium.io.photon.inoutput.c001_serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 如何实现对象克隆？ 答：有两种方式： 1). 实现Cloneable接口并重写Object类中的clone()方法； 2).
 * 实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆，代码如下。
 */
public class SerializableCloneTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Map<String, String> m = new HashMap<String, String>();
		m.put("k", "v");

		Test t = new Test(1, "s", m);
		Test t2 = clone(t);
		System.out.println(t);
		System.out.println(t2);
	}

	@SuppressWarnings("serial")
	static class Test implements java.io.Serializable {

		private int a;

		private String b;

		private Map<String, String> c;

		public Test() {

		}

		public Test(int a, String b, Map<String, String> c) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public int getA() {
			return a;
		}

		public void setA(int a) {
			this.a = a;
		}

		public String getB() {
			return b;
		}

		public void setB(String b) {
			this.b = b;
		}

		public Map<String, String> getC() {
			return c;
		}

		public void setC(Map<String, String> c) {
			this.c = c;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Test [a=" + a + ", b=" + b + ", c=" + c + "]";
		}
	}

	/**
	 * 注意：基于序列化和反序列化实现的克隆不仅仅是深度克隆， 更重要的是通过泛型限定，可以检查出要克隆的对象是否支持序列化，
	 * 这项检查是编译器完成的，不是在运行时抛出异常，这种是方案明显优于使用Object类的clone方法克隆对象。
	 * 让问题在编译的时候暴露出来总是优于把问题留到运行时。
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T obj) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bout);
		oos.writeObject(obj);

		ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bin);
		T readObject = (T) ois.readObject();
		return readObject;
		// 说明：调用ByteArrayInputStream或ByteArrayOutputStream对象的close方法没有任何意义
		// 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，这一点不同于对外部资源（如文件流）的释放
	}

}
