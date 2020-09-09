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

package xyz.flysium.photon.c000_bio.s04_object;

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
