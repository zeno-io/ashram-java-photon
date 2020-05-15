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
