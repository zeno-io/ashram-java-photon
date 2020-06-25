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

package com.github.flysium.io.photon.jvm.c010_jmm;

import jdk.internal.vm.annotation.Contended;

/**
 * 使用缓存行的对齐能够提高效率
 *
 * <li>
 * JDK8 需要缓存行保证的数据，可以增加注解： @Contended
 * </li>
 * <li>
 * 并 VM Option 增加： <pre>-XX:-RestrictContended</pre>
 * </li>
 * 注意比较 加不加 VM Option的两种情况
 *
 * <li>
 * 对于 Java 9 以上的版本，编译和运行时需要增加参数：
 * <pre>
 *     --add-exports=java.base/jdk.internal.vm.annotation=ALL-UNNAMED
 *    </pre>
 * <p>
 * 如果你是用 IntelliJ IDEA，由于IDEA 编译的时候会自动在 javac 后面添加 --release 这个参数，<br/> 这个参数不允许 --add-export等所有打破
 * Java 模块化的参数，所以需要关闭 IDEA 传递这个参数
 * </p>
 * </li>
 */
public class T02_Contended {

  private static class T {

    @Contended("group1")
    public volatile long x = 0L;
    @Contended("group2")
    public volatile long y = 0L;
  }

  public static T t = new T();

  public static void main(String[] args) throws Exception {
    Thread t1 = new Thread(() -> {
      for (long i = 0; i < 1000_0000L; i++) {
        t.x = i;
      }
    });

    Thread t2 = new Thread(() -> {
      for (long i = 0; i < 1000_0000L; i++) {
        t.y = i;
      }
    });

    final long start = System.nanoTime();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println((System.nanoTime() - start) / 100_0000);
  }
}
