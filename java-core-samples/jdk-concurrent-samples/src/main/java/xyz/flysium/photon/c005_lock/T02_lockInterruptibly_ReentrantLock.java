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
 * lockInterruptibly()
 *
 * @author Sven Augustus
 */
public class T02_lockInterruptibly_ReentrantLock {

  // lockInterruptibly() 阻塞式地获取锁，立即处理interrupt信息，并抛出异常

  static int count = 0;
  static ReentrantLock lock = new ReentrantLock();

  private static void increment() {
    try {
      lock.lockInterruptibly();
    } catch (InterruptedException e) {
      e.printStackTrace();
      // 捕获中断，结束
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

  public static void main(String[] args) throws InterruptedException {
    Thread[] threads = new Thread[100];
    for (int i = 0; i < 100; i++) {
      Thread t = new Thread(T02_lockInterruptibly_ReentrantLock::increment);
      threads[i] = t;
      t.start();
    }

    for (Thread t : threads) {
      t.join();
    }
    System.out.println("count=" + count);
  }

}
