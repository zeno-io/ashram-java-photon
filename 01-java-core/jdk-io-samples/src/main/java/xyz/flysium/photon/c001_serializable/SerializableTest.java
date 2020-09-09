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

package xyz.flysium.photon.c001_serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 对 Person 进行反序列化
 *
 * @author Sven Augustus
 * @version 2016年11月20日
 */
public class SerializableTest {

	public static void main(String[] args) {
		try {
			Person ted = new Person("Ted", "Neward", 39);
			Person charl = new Person("Charlotte", "Neward", 38);

			ted.setSpouse(charl);
			charl.setSpouse(ted);

			FileOutputStream fos = new FileOutputStream("tempdata.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(ted);
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			FileInputStream fis = new FileInputStream("tempdata.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Person ted = (Person) ois.readObject();
			ois.close();

			System.out.println(ted.getFirstName());
			System.out.println(ted.getAge());
			System.out.println(ted.getSpouse().getFirstName());
			System.out.println(ted.getSpouse().getAge());
			// System.out.println(ted.getSss());

			// Clean up the file
			// new File("tempdata.ser").delete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/**
		 * 为了使 Java 运行时相信两种类型实际上是一样的，第二版和随后版本的 Person 必须与第一版有相同的序列化版本 hash（存储为
		 * private static final serialVersionUID 字段）。 因此，我们需要 serialVersionUID
		 * 字段，它是通过对原始（或 V1）版本的 Person 类运行 JDK serialver 命令计算出的。
		 *
		 * 如果 需要 序列化 允许重构，特别是序列化的数据是存储起来的时候
		 * 务必在初始实现Serializable接口的同时，显式生成serialVersionUID，并在后续版本中不修改
		 * serialVersionUID的值。
		 */

	}

}
