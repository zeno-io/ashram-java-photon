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

package xyz.flysium.photon.c005_lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * tryLock()
 *
 * @author Sven Augustus
 */
public class T03_tryLock_ReentrantLock {

  // tryLock()  尝试获取锁，不管成功失败，都立即返回true、false，注意的是即使已将此锁设置为使用公平排序策略，tryLock()仍然可以打破公平性去插队抢占。
  // 如果希望遵守此锁的公平设置，则使用 tryLock(0, TimeUnit.SECONDS)，它几乎是等效的（也检测中断）。

  static int count = 0;
  static ReentrantLock lock = new ReentrantLock();

  private static void increment() {
    boolean acquired = lock.tryLock();
    // lock.tryLock(0, TimeUnit.SECONDS);
    if (!acquired) {
      // 没有获取到锁lock
      System.out.println("没有获取到锁lock");
      return;
    }
    try {
      for (int i = 0; i < 10000; i++) {
        count++;
      }
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    Thread[] threads = new Thread[100];
    for (int i = 0; i < 100; i++) {
      Thread t = new Thread(T03_tryLock_ReentrantLock::increment);
      threads[i] = t;
      t.start();
    }

    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println("count=" + count);
  }

}
