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
