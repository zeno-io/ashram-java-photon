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

package com.github.flysium.io.photon.juc.c003_volatile;

/**
 * synchronized 既可以保证可见性，还可以保证原子性。 volatile 只能保证可见性，不保证原子性。
 *
 * @author Sven Augustus
 */
public class T05_VolatileVsSync {

  // count 不是 1000000
  volatile static int count = 0;

  private static void increment() {
    for (int i = 0; i < 10000; i++) {
      count++;
    }
  }

  private static synchronized void synchronizedIncrement() {
    increment();
  }

  public static void main(String[] args) {
    Thread[] threads = new Thread[100];
    for (int i = 0; i < 100; i++) {
      Thread t = new Thread(() -> {
        increment();
        // synchronizedIncrement();
      });
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
