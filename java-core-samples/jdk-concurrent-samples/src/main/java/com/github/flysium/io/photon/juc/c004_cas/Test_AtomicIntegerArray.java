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

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * AtomicIntegerArray 用法
 *
 * @author Sven Augustus
 */
public class Test_AtomicIntegerArray {

  static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(
      new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

  public static void main(String[] args) throws InterruptedException {
    final int threadNum = 200;
    final int TIMES = 10;

    Thread[] threads = new Thread[threadNum];
    for (int i = 0; i < threadNum; i++) {
      final int index = i % TIMES;
      Thread t = new Thread(() -> {
        atomicIntegerArray.incrementAndGet(index);
      }, "t" + i);
      threads[i] = t;
      t.start();
    }

    for (Thread t : threads) {
      t.join();
    }

    for (int i = 0; i < atomicIntegerArray.length(); i++) {
      System.out.print(atomicIntegerArray.get(i) + " ");
      // Assert.assertEquals((threadNum / TIMES) + (i + 1), atomicIntegerArray.get(i));
    }
  }

}
