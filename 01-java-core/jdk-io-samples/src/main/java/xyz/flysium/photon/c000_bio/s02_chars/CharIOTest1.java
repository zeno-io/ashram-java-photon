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

package xyz.flysium.photon.c000_bio.s02_chars;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 字符输入输出流 - 基本
 *
 * @author Sven Augustus
 * @version 2017年1月23日
 */
public class CharIOTest1 {

	public static void main(String[] args) throws IOException {
		String filepath = "file.txt";

		java.io.Writer w = null;
		try {
			w = new FileWriter(filepath);

			w.write("何以飘零去，何以少团栾，何以别离久，何以不得安？ ");
			w.flush();// 把缓冲区内的数据刷新到磁盘

		} finally {
			if (w != null) {
				w.close();// 关闭流
			}
		}

		java.io.Reader r = null;
		try {
			r = new FileReader(filepath);

			/*
			 * int data = -1; while ((data = r.read()) != -1) { //操作数据
			 * System.out.print((char)data); }
			 */
			int len = -1;
			char[] chars = new char[2];
			while ((len = r.read(chars)) != -1) {
				// System.out.println(is.skip(100));
				// System.out.println(is.available());
				// 操作数据
				// System.out.print(((char) chars[0]));
				// System.out.print(((char) chars[1]));//////注意了 最后读取字符到 char[]
				// 数组可能没满的哦，需要根据返回len判定界限
				for (int i = 0; i < len; i++) {
					System.out.print(((char) chars[i]));
				}
			}
		} finally {
			if (r != null) {
				r.close();// 关闭流
			}
		}
	}

}
