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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * 读取文件
 *
 * @author Sven Augustus
 * @version 2017年1月23日
 */
public class ReadFileTest {

	public static void main(String[] args) throws IOException {
		readFileAsByte();

		System.out.println();

		readFileAsByteArray();

		System.out.println();

		readFileAsChar();

		System.out.println();

		readFileAsCharArray();
	}

	/**
	 * 单字节读取文件示例
	 */
	public static void readFileAsByte() throws IOException {
		String filepath = "file.bin";

		java.io.InputStream is = null;
		try {
			is = new FileInputStream(filepath);

			int data = -1;
			while ((data = is.read()) != -1) {// -1 表示读取到达文件结尾
				// 操作数据
				System.out.print((byte) data + " ");
			}
		} finally {
			if (is != null) {
				is.close();// 关闭流
			}
		}
	}

	/**
	 * 字节数组读取文件示例
	 */
	public static void readFileAsByteArray() throws IOException {
		String filepath = "file.bin";

		java.io.InputStream is = null;
		try {
			is = new BufferedInputStream(
					new FileInputStream(filepath));// 组装BufferedInputStream流，加入缓冲能力

			byte[] data = new byte[256];
			int len = -1;
			while ((len = is.read(data)) != -1) {// -1 表示读取到达文件结尾
				// 操作数据
				for (int i = 0; i < len; i++) {
					System.out.print(data[i] + " ");
				}
			}
		} finally {
			if (is != null) {
				is.close();// 关闭流
			}
		}
	}

	/**
	 * 单字符读取文件示例
	 */
	public static void readFileAsChar() throws IOException {
		String filepath = "file.txt";

		java.io.Reader r = null;
		try {
			r = new FileReader(filepath);

			int data = -1;
			while ((data = r.read()) != -1) {// -1 表示读取到达文件结尾
				// 操作数据
				System.out.print((char) data);
			}
		} finally {
			if (r != null) {
				r.close();// 关闭流
			}
		}
	}

	/**
	 * 字符数组读取文件示例
	 */
	public static void readFileAsCharArray() throws IOException {
		String filepath = "file.txt";

		java.io.Reader r = null;
		try {
			r = new BufferedReader(new FileReader(filepath));// 组装BufferedReader流，加入缓冲能力

			char[] data = new char[256];
			int len = -1;
			while ((len = r.read(data)) != -1) {// -1 表示读取到达文件结尾
				// 操作数据
				for (int i = 0; i < len; i++) {
					System.out.print(data[i]);
				}
			}
		} finally {
			if (r != null) {
				r.close();// 关闭流
			}
		}
	}

}
