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

package xyz.flysium.photon.c008_ref_and_threadlocal;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * 软引用
 *
 * @author Sven Augustus
 */
public class V02_SoftReference {

  // 如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。
  // 只要垃圾回收器没有回收它，该对象就可以被程序使用。
  // 软引用可用来实现内存敏感的高速缓存。

  //  软引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的对象被垃圾回收器回收，Java虚拟机就会把这个软引用加入到与之关联的引用队列中。

  //-Xmx24m
  public static void main(String[] args) throws IOException, InterruptedException {
    // 10m
    SoftReference<byte[]> sr = new SoftReference<byte[]>(new byte[1024 * 1024 * 10]);
    System.out.println(sr.get());

    System.gc();
    TimeUnit.MILLISECONDS.sleep(500);
    System.out.println(sr.get());

    // 15m
    // 再分配一个数组，heap将装不下，这时候系统会垃圾回收，先回收一次，如果不够，会把软引用干掉
    byte[] m2 = new byte[1024 * 1024 * 15];
    System.out.println(sr.get());
  }

}
