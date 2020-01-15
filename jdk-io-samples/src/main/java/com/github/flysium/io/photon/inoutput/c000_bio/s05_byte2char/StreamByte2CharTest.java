package com.github.flysium.io.photon.inoutput.c000_bio.s05_byte2char;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * InputStreamReader与OutputStreamWriter转化流示例
 *
 * @author Sven Augustus
 * @version 2017年1月27日
 */
public class StreamByte2CharTest {

	public static void main(String[] args) throws IOException {
		String filepath = "file.txt";

		java.io.Writer w = null;
		try {
			w = new OutputStreamWriter(new FileOutputStream(filepath));

			w.write("何以飘零去，何以少团栾，何以别离久，何以不得安？ ");
			w.flush();// 把缓冲区内的数据刷新到磁盘

		} finally {
			if (w != null) {
				w.close();// 关闭流
			}
		}

		java.io.Reader r = null;
		try {
			r = new InputStreamReader(new FileInputStream(filepath));

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
