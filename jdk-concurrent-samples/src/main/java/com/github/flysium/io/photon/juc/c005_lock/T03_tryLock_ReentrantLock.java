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

package com.github.flysium.io.photon.juc.c005_lock;

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

  private  static void increment() {
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
