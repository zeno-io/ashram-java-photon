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
 * ReentrantLock vs synchronized
 *
 * @author Sven Augustus
 */
public class T01_ReentrantLock {

  // 使用 synchronized 锁定的话如果遇到异常，jvm会自动释放锁，但是 lock 必须手动释放锁，因此经常在finally中进行锁的释放

  static int count = 0;
  static ReentrantLock lock = new ReentrantLock();

  private  /*synchronized*/ static void increment() {
    // lock()  阻塞式地获取锁，只有在获取到锁后才处理interrupt信息
    lock.lock();
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
      Thread t = new Thread(T01_ReentrantLock::increment);
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
