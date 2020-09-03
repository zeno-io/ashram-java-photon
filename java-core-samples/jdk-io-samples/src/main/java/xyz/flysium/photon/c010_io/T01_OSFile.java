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

package xyz.flysium.photon.c010_io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 测试步骤： 验证不同的IO方式中产生的系统调用 system call的方式不同，以及 page cache的作用
 * <li>
 * 使用源码编译并写 mysh.sh 脚本
 * </li>
 * <pre>
 *      mkdir /root/testfileio
 *      cd  /root/testfileio
 *      vi T01_OSFile.java
 *      javac T01_OSFile.java
 *      vi mysh.sh
 * </pre>
 * <li>
 * mysh.sh 脚本内容：
 * <pre>
 *      #!/bin/bash
 *      if [ -z $1 ] ; then
 *        echo 'Usage $0:(0|1|2)'
 *        exit 1
 *      fi
 *      rm -rf out
 *      rm -rf out.*
 *      rm -rf data.txt
 *      strace -ff -o out java T01_OSFile $1
 * </pre>
 * 测试：
 * <pre>
 *   ./mysh.sh 0
 * </pre>
 * </li>
 * <li>
 * 通过 pcstat 查看 page cache的命中情况
 * <pre>
 *      cd /root/testfileio
 *      ls -lh && pcstat data.txt
 *  </pre>
 * 通过 cat out 查看 strace 追踪程序执行过程的系统调用 system call 情况
 * <pre>
 *   -> ls
 *   -rw-r--r-- 1 root root  13890 Jun 29 15:27 out.4370
 *   -rw-r--r-- 1 root root 785235 Jun 29 15:27 out.4371
 * </pre>
 * <pre>
 *    cat out.4371 | grep 123456789
 * </pre>
 * </li>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T01_OSFile {

  static byte[] data = "123456789\n".getBytes();
  static String path = "/root/testfileio/data.txt";

  public static void main(String[] args) throws Exception {
    switch (args[0]) {
      case "0":
        testBasicFileIO();
        break;
      case "1":
        testBufferedFileIO();
        break;
      case "2":
        testRandomAccessFileWrite();
      default:
    }
  }

  // 最基本的file写, 通过 strace追踪的 系统调用情况：
  //  write(4, "123456789\n", 10)             = 10
  //  write(4, "123456789\n", 10)             = 10
  //  write(4, "123456789\n", 10)             = 10
  //  write(4, "123456789\n", 10)             = 10
  //  write(4, "123456789\n", 10)             = 10
  //  write(4, "123456789\n", 10)             = 10
  //  write(4, "123456789\n", 10)             = 10
  //  write(4, "123456789\n", 10)             = 10
  public static void testBasicFileIO() throws Exception {
    File file = new File(path);
    FileOutputStream out = new FileOutputStream(file);
    while (true) {
      Thread.sleep(10);
      out.write(data);
    }
  }

  // 测试buffer文件IO, 通过 strace追踪的 系统调用情况：
  // write(4, "123456789\n123456789\n123456789\n12"..., 8190) = 8190
  // write(4, "123456789\n123456789\n123456789\n12"..., 8190) = 8190
  // write(4, "123456789\n123456789\n123456789\n12"..., 8190) = 8190
  public static void testBufferedFileIO() throws Exception {
    File file = new File(path);
    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
    while (true) {
      Thread.sleep(10);
      out.write(data);
    }
  }

  // 测试文件NIO
  //[root@node30 testfileio]# lsof -p 4170
  //COMMAND  PID USER   FD   TYPE             DEVICE  SIZE/OFF     NODE NAME
  //...
  //java    4170 root  mem    REG              253,0      4096 50848955 /root/testfileio/data.txt
  //java    4170 root    0u   CHR              136,1       0t0        4 /dev/pts/1
  //java    4170 root    1u   CHR              136,1       0t0        4 /dev/pts/1
  //java    4170 root    2u   CHR              136,1       0t0        4 /dev/pts/1
  //java    4170 root    3r   REG              253,0  73844502 51498485 /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64/jre/lib/rt.jar
  //java    4170 root    4u   REG              253,0      4096 50848955 /root/testfileio/data.txt
  // 以上表示 mmap的内存文件映射： java    4170 root  mem    REG              253,0      4096 50848955 /root/testfileio/data.txt
  // 以上表示 普通文件的读写：  java    4170 root    4u   REG              253,0      4096 50848955 /root/testfileio/data.txt
  //
  //  通过 strace追踪的 系统调用情况：
  //  write(4, "hello randomaccessfile\n", 23) = 23
  //  write(4, "hello pagecache\n", 16)       = 16
  //
  //  write(1, "write------------", 17)       = 17
  //  write(1, "\n", 1)                       = 1   #System.out.println("write------------");
  //
  //  read(0, "\n", 8192)                     = 1  # System.in.read();
  //
  //  lseek(4, 4, SEEK_SET)                   = 4
  //  write(4, "XYZ", 3)                      = 3
  //
  //  write(1, "seek---------", 13)           = 13
  //  write(1, "\n", 1)                       = 1
  //
  //  read(0, "\n", 8192)                     = 1  # System.in.read();
  //
  //  mmap(NULL, 4096, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS, -1, 0) = 0x7fa938207000 # MappedByteBuffer
  //
  //  write(1, "map--put--------", 16)        = 16  # System.out.println("map--put--------");
  //  write(1, "\n", 1)                       = 1
  public static void testRandomAccessFileWrite() throws Exception {
    RandomAccessFile raf = new RandomAccessFile(path, "rw");

    raf.write("hello randomaccessfile\n".getBytes());
    raf.write("hello pagecache\n".getBytes());
    System.out.println("write------------");
    System.in.read();

    raf.seek(4);
    raf.write("XYZ".getBytes());

    System.out.println("seek---------");
    System.in.read();

    FileChannel rafchannel = raf.getChannel();
    //mmap  堆外  和文件映射的
    MappedByteBuffer map = rafchannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);

    map.put("@@@".getBytes());  //不是系统调用  但是数据会到达 内核的 page cache
    // 曾经我们是需要out.write()  这样的系统调用，才能让程序的data 进入内核的page cache
    // 曾经必须有用户态内核态切换
    // mmap的内存映射，依然是内核的page cache体系所约束的！！！
    // 换言之，丢数据
    // 你可以去github上找一些 其他C程序员写的jni扩展库，使用linux内核的Direct IO
    // 直接IO是忽略linux的page cache
    // 是把page cache  交给了程序自己开辟一个字节数组当作page cache，动用代码逻辑来维护一致性/dirty。。。一系列复杂问题

    System.out.println("map--put--------");
    System.in.read();

//        map.force(); //  flush

    raf.seek(0);

    ByteBuffer buffer = ByteBuffer.allocate(8192);
//        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    int read = rafchannel.read(buffer);   //buffer.put()
    System.out.println(buffer);
    buffer.flip();
    System.out.println(buffer);

    for (int i = 0; i < buffer.limit(); i++) {
      Thread.sleep(200);
      System.out.print(((char) buffer.get(i)));
    }
  }

}
