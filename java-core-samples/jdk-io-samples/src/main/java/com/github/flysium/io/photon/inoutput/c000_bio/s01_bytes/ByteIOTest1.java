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

package com.github.flysium.io.photon.inoutput.c000_bio.s01_bytes;

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
