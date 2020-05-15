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

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ABA问题
 *
 * @author Sven Augustus
 */
public class T04_ABA_AtomicReference {

  // CAS需要在操作值的时候检查下值有没有发生变化，如果没有发生变化则更新，
  // 但是如果一个值原来是A，变成了B，又变成了A，那么使用CAS进行检查时会发现它的值没有发生变化，
  // 但是实际上却变化了。这就是CAS的ABA问题。

  static AtomicReference<Long> atomicReference = new AtomicReference<Long>(1L);

  public static void main(String[] args) {
    Random random = new Random();

    for (int i = 0; i < 100; i++) {
      new Thread(() -> {
        try {
          Thread.sleep(random.nextInt(100));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (atomicReference.compareAndSet(1L, 2L)) {
          System.out.println(Thread.currentThread().getName() + " 获得了锁进行了对象修改！");
        }
      }, "t" + i).start();
    }
    new Thread(() -> {
      while (!atomicReference.compareAndSet(2L, 1L)) {
      }
      System.out.println(Thread.currentThread().getName() + " 已经改回原始值！");
    }, "b").start();
  }

}
