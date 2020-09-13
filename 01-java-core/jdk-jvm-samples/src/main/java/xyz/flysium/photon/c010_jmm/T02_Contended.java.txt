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

package xyz.flysium.photon.c010_jmm;

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
