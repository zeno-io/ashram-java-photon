package com.github.flysium.io.photon.inoutput.c000_bio.s06_parse;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * LineNumberReader
 *
 * @author Sven Augustus
 * @version 2017年1月28日
 */
public class LineNumTest {

	public static void main(String[] args) throws IOException {
		String filepath = "file.txt";

		java.io.Writer w = null;
		try {
			w = new FileWriter(filepath);

			w.write("百世山河任凋换，一生意气未改迁。愿从劫火投身去，重自寒灰飞赤鸾。\r\n");
			w.write("沧海桑田新几度，月明还照旧容颜。琴心剑魄今何在，留见星虹贯九天。 \n");
			w.write("冰轮腾转下西楼，永夜初晗凝碧天。长路寻仙三山外，道心自在红尘间。 \n");
			w.write("何来慧剑破心茧，再把貂裘换酒钱。回望天涯携手处，踏歌重访白云间。\n");
			w.write("何以飘零去，何以少团栾，何以别离久，何以不得安？ \n");
			w.flush();// 把缓冲区内的数据刷新到磁盘

		} finally {
			if (w != null) {
				w.close();// 关闭流
			}
		}
		/**
		 * LineNumberReader
		 */
		LineNumberReader lnr = null;
		try {
			lnr = new LineNumberReader(new FileReader(filepath));
			int len = -1;
			char[] chars = new char[2];
			// int lastLineNumber = -1;
			while ((len = lnr.read(chars)) != -1) {

				for (int i = 0; i < len; i++) {
					System.out.print(((char) chars[i]));
				}
				/*
				 * int lineNumber = lnr.getLineNumber(); if (lineNumber != lastLineNumber) {
				 * System.out.println("---------------行数：" + lineNumber); lastLineNumber =
				 * lineNumber; }
				 */
			}
			int lineNumber = lnr.getLineNumber();
			System.out.println("行数：" + lineNumber);
			System.out.println();
		} finally {
			if (lnr != null) {
				lnr.close();
			}
		}
	}

}
