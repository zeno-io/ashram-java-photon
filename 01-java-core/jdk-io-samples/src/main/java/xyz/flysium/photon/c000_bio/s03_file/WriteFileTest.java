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
   * <p>
   * 涉及的系统调用：
   * <pre>
   * openat(AT_FDCWD, "file.bin", O_WRONLY|O_CREAT|O_TRUNC, 0666) = 4
   * fstat(4, {st_mode=S_IFREG|0644, st_size=0, ...}) = 0
   * write(4, "1", 1)                        = 1
   * write(4, "2", 1)                        = 1
   * write(4, "3", 1)                        = 1
   * write(4, "4", 1)                        = 1
   * close(4)
   * </pre>
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
   * <p>
   * 涉及的系统调用：
   * <pre>
   * openat(AT_FDCWD, "file.bin", O_WRONLY|O_CREAT|O_TRUNC, 0666) = 4
   * fstat(4, {st_mode=S_IFREG|0644, st_size=0, ...}) = 0
   * write(4, "\224\210v\262\331P\276U\37\236\206\207\307\325A\321/mE\344\344\363\27\344\276\vk4=Xm\343"..., 256 <unfinished ...>
   * close(4 <unfinished ...>
   * </pre>
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
