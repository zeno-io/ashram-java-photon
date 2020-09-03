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

package xyz.flysium.photon.c007_AQS;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 深入 AbstractQueuedSynchronizer 源码
 *
 * @author Sven Augustus
 */
public class StartToDeepInAQS_02_Condition {

  static ReentrantLock lock = new ReentrantLock(true);
  static Condition c1 = lock.newCondition();
  static Condition c2 = lock.newCondition();

  public static void main(String[] args) {
    new Thread(() -> {
      lock.lock();
      try {
        for (int i = 0; i < 3; i++) {
          System.out.println(Thread.currentThread().getName() + " " + i);
          c2.signal();
          c1.await();
        }
        c2.signal();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }, "A").start();

    new Thread(() -> {
      lock.lock();
      try {
        for (int i = 0; i < 3; i++) {
          System.out.println(Thread.currentThread().getName() + " " + i);
          c1.signal();
          c2.await();
        }
        c1.signal();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }, "B").start();
  }

}
