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

package xyz.flysium.photon.c002_nio.s02_file_channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * FileChannel读写文件示例
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class FileChannelTest1 {

  public static void main(String[] args) throws IOException {
    final String path = "file.txt";

    // 写文件
    write(path);
    // 特定位置读写
    write2(path);
    // 读文件
    read(path);
    System.out.println();
    // 文件截取
    truncate(path);
    // 读文件
    read(path);
  }

  /**
   * FileChannel 读文件
   */
  private static void read(String path) throws IOException {

    try (FileInputStream fis = new FileInputStream(path);
        java.nio.channels.FileChannel channel = fis.getChannel()) {

      ByteBuffer buffer1 = ByteBuffer.allocate(1024);
      // 从入channel读取数据到buffer
      buffer1.rewind();
      while (channel.read(buffer1) > 0) {
        // 读取buffer
        buffer1.flip();
        Charset charset = Charset.defaultCharset();
        CharBuffer charBuffer = charset.decode(buffer1);
        System.out.print(charBuffer);
      }
    }
  }

  /**
   * FileChannel 写文件
   */
  private static void write(String path) throws IOException {
    ByteBuffer buffer = ByteBuffer.wrap("赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。\n".getBytes());

    try (FileOutputStream fos = new FileOutputStream(path);
        java.nio.channels.FileChannel channel = fos.getChannel()) {
      // 强制刷出到内存
      channel.force(true);

      // 从buffer读取数据写入channel
      buffer.rewind();
      channel.write(buffer);
    }
  }

  /**
   * FileChannel 特定位置读写
   */
  private static void write2(String path) throws IOException {
    ByteBuffer buffer = ByteBuffer.wrap("十步杀一人，千里不留行。事了拂衣去，深藏身与名。\n".getBytes());

    try (RandomAccessFile file = new RandomAccessFile(path, "rw");
        java.nio.channels.FileChannel channel = file.getChannel()) {
      // 定位到文件末尾
      channel.position(channel.size());
      // 强制刷出到内存
      channel.force(true);

      // 从buffer读取数据写入channel
      buffer.rewind();
      channel.write(buffer);
    }
  }

  /**
   * FileChannel 文件截取
   */
  private static void truncate(String path) throws IOException {
    try (RandomAccessFile file = new RandomAccessFile(path, "rw");
        java.nio.channels.FileChannel channel = file.getChannel()) {

      // 截取文件前36byte
      channel.truncate(36);
    }
  }

}
