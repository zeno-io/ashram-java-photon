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
