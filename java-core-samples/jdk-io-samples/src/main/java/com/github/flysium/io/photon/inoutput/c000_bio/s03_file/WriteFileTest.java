package com.github.flysium.io.photon.inoutput.c000_bio.s03_file;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * 写入文件
 *
 * @author Sven Augustus
 * @version 2017年1月23日
 */
public class WriteFileTest {

	public static void main(String[] args) throws IOException {
		writeFileAsByte();

		System.out.println();

		writeFileAsByteArray();

		System.out.println();

		writeFileAsChar();

		System.out.println();

		writeFileAsCharArray();
	}

	/**
	 * 单字节写入文件示例
	 */
	public static void writeFileAsByte() throws IOException {
		String filepath = "file.bin";

		java.io.OutputStream os = null;
		try {
			os = new FileOutputStream(filepath);

			os.write('1');
			os.write('2');
			os.write('3');
			os.write('4');
			os.flush();// 把缓冲区内的数据刷新到磁盘

		} finally {
			if (os != null) {
				os.close();// 关闭流
			}
		}
	}

	/**
	 * 字节数组写入文件示例
	 */
	public static void writeFileAsByteArray() throws IOException {
		String filepath = "file.bin";

		java.io.OutputStream os = null;
		try {
			os = new BufferedOutputStream(
					new FileOutputStream(filepath));// 组装BufferedOutputStream流，加入缓冲能力

			// 模拟
			byte[] data = new byte[256];
			new Random().nextBytes(data);

			os.write(data);
			os.flush();// 把缓冲区内的数据刷新到磁盘

		} finally {
			if (os != null) {
				os.close();// 关闭流
			}
		}
	}

	/**
	 * 单字符写入文件示例
	 */
	public static void writeFileAsChar() throws IOException {
		String filepath = "file.txt";

		java.io.Writer w = null;
		try {
			w = new FileWriter(filepath);

			w.write('1');
			w.write('2');
			w.write('3');
			w.write('4');
			w.flush();// 把缓冲区内的数据刷新到磁盘

		} finally {
			if (w != null) {
				w.close();// 关闭流
			}
		}
	}

	/**
	 * 字符数组写入文件示例
	 */
	public static void writeFileAsCharArray() throws IOException {
		String filepath = "file.txt";

		java.io.Writer w = null;
		try {
			w = new BufferedWriter(new FileWriter(filepath));// 组装BufferedWriter流，加入缓冲能力

			// 模拟
			char[] data = new char[256];
			String f = "0123456789abcdefghijklmnopqrstuvwxyz";
			Random rd = new Random();
			for (int i = 0; i < data.length; i++) {
				data[i] = f.charAt(rd.nextInt(f.length()));
			}

			w.write(data);
			w.flush();// 把缓冲区内的数据刷新到磁盘

		} finally {
			if (w != null) {
				w.close();// 关闭流
			}
		}
	}

}
