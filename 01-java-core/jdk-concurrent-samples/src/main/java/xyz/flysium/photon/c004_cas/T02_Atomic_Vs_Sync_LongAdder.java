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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * AtomicLong 、synchronized、LongAdder
 */
public class T02_Atomic_Vs_Sync_LongAdder {

  static final int nThreads = 1000;
  static AtomicLong count1 = new AtomicLong(0L);
  static long count2 = 0L;
  static LongAdder count3 = new LongAdder();

  public static void main(String[] args) throws Exception {
    long total = timeTasks(nThreads, () -> {
      for (int k = 0; k < 100000; k++) {
        count1.incrementAndGet();
      }
    });
    System.out.println("Atomic: " + count1.get() + " time " + total);
    //-----------------------------------------------------------
    final Object lock = new Object();
    total = timeTasks(nThreads, () -> {
      for (int k = 0; k < 100000; k++) {
        synchronized (lock) {
          count2++;
        }
      }
    });
    System.out.println("Sync: " + count2 + " time " + total);
    //-----------------------------------------------------------
    total = timeTasks(nThreads, () -> {
      for (int k = 0; k < 100000; k++) {
        count3.increment();
      }
    });
    System.out.println("LongAdder: " + count2 + " time " + total);
  }

  private static long timeTasks(final int nThreads, Runnable runnable) {
    CountDownLatch startSignal = new CountDownLatch(1);
    CountDownLatch doneSignal = new CountDownLatch(nThreads);
    for (int i = 0; i < nThreads; i++) {
      new Thread(() -> {
        try {
          startSignal.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        try {
          runnable.run();
        } finally {
          doneSignal.countDown();
        }
      }).start();
    }
    startSignal.countDown();
    long start = System.currentTimeMillis();
    try {
      doneSignal.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return System.currentTimeMillis() - start;
  }

  static void microSleep(int m) {
    try {
      TimeUnit.MICROSECONDS.sleep(m);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
