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
 * ReentrantLock 可以指定为公平锁
 *
 * @author Sven Augustus
 */
public class T04_Fairness {

  // 参数为true表示为公平锁，请对比输出结果
  static ReentrantLock lock = new ReentrantLock(true);

  public static void main(String[] args) {
    Runnable r = () -> {
      for (int i = 0; i < 100; i++) {
        lock.lock();
        try {
          System.out.println(Thread.currentThread().getName() + " 获得锁");
        } finally {
          lock.unlock();
        }
      }
    };
    new Thread(r, "t1").start();
    new Thread(r, "t2").start();
  }

}
