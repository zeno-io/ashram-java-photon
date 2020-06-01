/*
 * Copyright 2020 SvenAugustus
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

package com.github.flysium.io.photon.inoutput.c002_nio.s02_file_channel;

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
