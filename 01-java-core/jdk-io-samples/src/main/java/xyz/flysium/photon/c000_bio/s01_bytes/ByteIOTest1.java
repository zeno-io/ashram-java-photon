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

package xyz.flysium.photon.c000_bio.s01_bytes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字节输入输出流 - 基本
 *
 * @author Sven Augustus
 * @version 2017年1月23日
 */
public class ByteIOTest1 {

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

		java.io.InputStream is = null;
		try {
			is = new FileInputStream(filepath);

			/*
			 * int data = -1; while ((data = is.read()) != -1) { //操作数据
			 * System.out.print((byte)data); }
			 */
			int len = -1;
			byte[] bytes = new byte[2];
			while ((len = is.read(bytes)) != -1) {
				// System.out.println(is.skip(100));
				// System.out.println(is.available());
				// 操作数据
				/*
				 * System.out.print(((char) bytes[0])); System.out.print(((char) bytes[1]));
				 */////// 注意了 最后读取字节到 byte[] 数组可能没满的哦，需要根据返回len判定界限
				for (int i = 0; i < len; i++) {
					System.out.print(((char) bytes[i]));
				}
			}
		} finally {
			if (is != null) {
				is.close();// 关闭流
			}
		}
	}

}
