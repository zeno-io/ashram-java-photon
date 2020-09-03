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
   * <p>
   * 涉及的系统调用：
   * <pre>
   *  openat(AT_FDCWD, "file.bin", O_RDONLY) = 4
   *  fstat(4, {st_mode=S_IFREG|0644, st_size=256, ...}) = 0
   *  read(4, "xx\234\34\313\234\233\236Uo\265\260\rW\247\"'\"m\0\364\330\206\227\362\373\206\10uZ<\""..., 8192) = 256
   *  read(4, "", 8192)                 = 0
   *  ...
   *  close(4)                          = 0
   *  </pre>
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
