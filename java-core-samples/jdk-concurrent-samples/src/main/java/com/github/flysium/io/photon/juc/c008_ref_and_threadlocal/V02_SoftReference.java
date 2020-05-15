/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.juc.c008_ref_and_threadlocal;

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
