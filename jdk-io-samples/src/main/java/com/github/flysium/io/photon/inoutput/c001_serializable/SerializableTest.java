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
