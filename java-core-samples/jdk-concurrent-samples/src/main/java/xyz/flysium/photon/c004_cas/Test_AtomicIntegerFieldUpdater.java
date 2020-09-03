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

package xyz.flysium.photon.c004_cas;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdater 用法
 *
 * @author Sven Augustus
 */
public class Test_AtomicIntegerFieldUpdater {

  static class Data {

    volatile int intValue = 0;
  }

  //  Updater使用限制：
  // 限制1：操作的目标不能是static类型。
  // 限制2：操作的目标不能是final类型的，因为final根本没法修改。
  // 限制3：必须是volatile类型的数据，也就是数据本身是读一致的。
  // 限制4：属性必须对当前的Updater所在的区域是可见的。

  static AtomicIntegerFieldUpdater<Data> updater = AtomicIntegerFieldUpdater
      .newUpdater(Data.class, "intValue");

  public static void main(String[] args) throws InterruptedException {
    final int threadNum = 100;

    Thread[] threads = new Thread[threadNum];
    final Data a = new Data();
    for (int i = 0; i < threadNum; i++) {
      Thread t = new Thread(() -> {
        updater.incrementAndGet(a);
      }, "t" + i);
      threads[i] = t;
      t.start();
    }

    for (Thread t : threads) {
      t.join();
    }

    System.out.println(updater.get(a));
    System.out.println(threadNum == updater.get(a));
  }

}
