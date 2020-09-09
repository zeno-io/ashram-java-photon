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

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * AtomicIntegerArray 用法
 *
 * @author Sven Augustus
 */
public class Test_AtomicIntegerArray {

  static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(
      new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

  public static void main(String[] args) throws InterruptedException {
    final int threadNum = 200;
    final int TIMES = 10;

    Thread[] threads = new Thread[threadNum];
    for (int i = 0; i < threadNum; i++) {
      final int index = i % TIMES;
      Thread t = new Thread(() -> {
        atomicIntegerArray.incrementAndGet(index);
      }, "t" + i);
      threads[i] = t;
      t.start();
    }

    for (Thread t : threads) {
      t.join();
    }

    for (int i = 0; i < atomicIntegerArray.length(); i++) {
      System.out.print(atomicIntegerArray.get(i) + " ");
      // Assert.assertEquals((threadNum / TIMES) + (i + 1), atomicIntegerArray.get(i));
    }
  }

}
