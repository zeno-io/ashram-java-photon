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

package com.github.flysium.io.photon.juc.c004_cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子操作类，AtomXXX类本身方法都是原子性的，但不能保证多个方法连续调用是原子性的
 *
 * @author Sven Augustus
 */
public class T01_AtomicInteger {

  /*     volatile static int count = 0;*/
  static AtomicInteger count = new AtomicInteger(0);

  private  /*synchronized*/ static void increment() {
    for (int i = 0; i < 10000; i++) {
      // count++;
      count.incrementAndGet();
    }
  }

  public static void main(String[] args) {
    Thread[] threads = new Thread[100];
    for (int i = 0; i < 100; i++) {
      Thread t = new Thread(T01_AtomicInteger::increment);
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
    System.out.println("count=" + count.get());
  }

}
