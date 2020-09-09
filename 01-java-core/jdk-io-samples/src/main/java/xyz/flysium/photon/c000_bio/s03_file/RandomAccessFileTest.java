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

package xyz.flysium.photon.c000_bio.s03_file;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile测试
 *
 * @author Sven Augustus
 * @version 2017年1月25日
 */
public class RandomAccessFileTest {

	public static void main(String[] args) throws IOException {
		RandomAccessFile file = null;
		try {
			file = new java.io.RandomAccessFile("file.bin", "rw");

			file.seek(0);

			file.writeChar('1');
			file.seek(0);
			System.out.println(file.readChar());

			/**
			 * 读取
			 */
			int data = -1;
			while ((data = file.read()) != -1) {// -1 表示读取到达文件结尾
				// 操作数据
				System.out.print((byte) data + " ");
			}

		} finally {
			if (file != null) {
				file.close();// 关闭流
			}
		}

	}

}
