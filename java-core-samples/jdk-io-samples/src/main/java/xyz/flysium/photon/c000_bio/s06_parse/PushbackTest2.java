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

package xyz.flysium.photon.c000_bio.s06_parse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PushbackReader;

/**
 * PushbackReader
 *
 * @author Sven Augustus
 * @version 2017年1月27日
 */
public class PushbackTest2 {

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
		/**
		 * 回推（pushback）
		 */
		PushbackReader pr = null;
		try {
			// pr = new PushbackReader(new FileReader(filepath));
			pr = new PushbackReader(new FileReader(filepath), 3);
			int len = -1;
			char[] chars = new char[2];
			while ((len = pr.read(chars)) != -1) {

				if ('飘' == chars[0]) {
					// pis.unread('U');
					// pis.unread(bytes);
					pr.unread(new char[]{'1', '2', '3'});
				}

				for (int i = 0; i < len; i++) {
					System.out.print(((char) chars[i]));
				}
			}
			System.out.println();
		} finally {
			if (pr != null) {
				pr.close();
			}
		}
		/**
		 * 会发现PushbackInputStream并没有改变目标介质的数据，不破坏流
		 */
		java.io.Reader r = null;
		try {
			r = new FileReader(filepath);

			int len = -1;
			char[] chars = new char[2];
			while ((len = r.read(chars)) != -1) {
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
