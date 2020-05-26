/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.inoutput.c000_bio.s04_object;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ObjectInputStream与ObjectOutputStream
 *
 * @author Sven Augustus
 * @version 2017年1月27日
 */
public class ObjectTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		class A implements java.io.Serializable {

			private static final long serialVersionUID = -9115696482036699559L;

			private int i = 1;
			private float f = 3;
			private String s = "风策信";

			public A() {
				super();
			}

			public A(int i, float f, String s) {
				super();
				this.i = i;
				this.f = f;
				this.s = s;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("A [i=").append(i).append(", f=").append(f).append(", s=").append(s)
						.append("]");
				return builder.toString();
			}
		}
		class B implements java.io.Serializable {

			private static final long serialVersionUID = 6124575321340728225L;

			private long i = 2;
			private double f = 4;
			private String str = "风策信";

			public B() {
				super();
			}

			public B(long i, double f, String str) {
				super();
				this.i = i;
				this.f = f;
				this.str = str;
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("B [i=").append(i).append(", f=").append(f).append(", str=")
						.append(str).append("]");
				return builder.toString();
			}
		}
		A a = new A(1, 3, "a");
		B b = new B(2, 4, "b");
		// System.out.println(a);
		// System.out.println(b);

		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("object.data.bin"));
			oos.writeObject(a);
			oos.writeObject(b);

			oos.flush();// 把缓冲区内的数据刷新到磁盘
		} finally {
			if (oos != null) {
				oos.close();
			}
		}
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("object.data.bin"));
			A a1 = (A) ois.readObject();
			B b1 = (B) ois.readObject();

			System.out.println(a1);
			System.out.println(b1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
	}

}
