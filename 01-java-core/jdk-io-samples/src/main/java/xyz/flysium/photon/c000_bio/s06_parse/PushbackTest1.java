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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

/**
 * PushbackInputStream
 *
 * @author Sven Augustus
 * @version 2017年1月27日
 */
public class PushbackTest1 {

	public static void main(String[] args) throws IOException {
		String filepath = "file.bin";

		java.io.OutputStream os = null;
		try {
			os = new FileOutputStream(filepath);

			os.write('#');
			os.write(new byte[]{'a', 'b', 'c', 'd'});
			os.flush();// 把缓冲区内的数据刷新到磁盘

		} finally {
			if (os != null) {
				os.close();// 关闭流
			}
		}
		/**
		 * 回推（pushback）
		 */
		PushbackInputStream pis = null;
		try {
			// pis = new PushbackInputStream(new FileInputStream(filepath));
			pis = new PushbackInputStream(new FileInputStream(filepath), 3);
			int len = -1;
			byte[] bytes = new byte[2];
			while ((len = pis.read(bytes)) != -1) {

				if ('b' == bytes[0]) {
					// pis.unread('U');
					// pis.unread(bytes);
					pis.unread(new byte[]{'1', '2', '3'});
				}

				for (int i = 0; i < len; i++) {
					System.out.print(((char) bytes[i]));
				}
			}
			System.out.println();
		} finally {
			if (pis != null) {
				pis.close();
			}
		}
		/**
		 * 会发现PushbackInputStream并没有改变目标介质的数据，不破坏流
		 */
		try {
			pis = new PushbackInputStream(new FileInputStream(filepath));
			int len = -1;
			byte[] bytes = new byte[2];
			while ((len = pis.read(bytes)) != -1) {
				for (int i = 0; i < len; i++) {
					System.out.print(((char) bytes[i]));
				}
			}
		} finally {
			if (pis != null) {
				pis.close();
			}
		}
	}

}
