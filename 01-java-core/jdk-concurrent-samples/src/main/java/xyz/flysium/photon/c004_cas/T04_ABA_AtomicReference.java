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

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ABA问题
 *
 * @author Sven Augustus
 */
public class T04_ABA_AtomicReference {

  // CAS需要在操作值的时候检查下值有没有发生变化，如果没有发生变化则更新，
  // 但是如果一个值原来是A，变成了B，又变成了A，那么使用CAS进行检查时会发现它的值没有发生变化，
  // 但是实际上却变化了。这就是CAS的ABA问题。

  static AtomicReference<Long> atomicReference = new AtomicReference<Long>(1L);

  public static void main(String[] args) {
    Random random = new Random();

    for (int i = 0; i < 100; i++) {
      new Thread(() -> {
        try {
          Thread.sleep(random.nextInt(100));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (atomicReference.compareAndSet(1L, 2L)) {
          System.out.println(Thread.currentThread().getName() + " 获得了锁进行了对象修改！");
        }
      }, "t" + i).start();
    }
    new Thread(() -> {
      while (!atomicReference.compareAndSet(2L, 1L)) {
      }
      System.out.println(Thread.currentThread().getName() + " 已经改回原始值！");
    }, "b").start();
  }

}
