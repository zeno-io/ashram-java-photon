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

package com.github.flysium.io.photon.inoutput.c000_bio.s03_file;

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
