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

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdater 用法
 *
 * @author Sven Augustus
 */
public class Test_AtomicIntegerFieldUpdater {

  static class Data {

    volatile int intValue = 0;
  }

  //  Updater使用限制：
  // 限制1：操作的目标不能是static类型。
  // 限制2：操作的目标不能是final类型的，因为final根本没法修改。
  // 限制3：必须是volatile类型的数据，也就是数据本身是读一致的。
  // 限制4：属性必须对当前的Updater所在的区域是可见的。

  static AtomicIntegerFieldUpdater<Data> updater = AtomicIntegerFieldUpdater
      .newUpdater(Data.class, "intValue");

  public static void main(String[] args) throws InterruptedException {
    final int threadNum = 100;

    Thread[] threads = new Thread[threadNum];
    final Data a = new Data();
    for (int i = 0; i < threadNum; i++) {
      Thread t = new Thread(() -> {
        updater.incrementAndGet(a);
      }, "t" + i);
      threads[i] = t;
      t.start();
    }

    for (Thread t : threads) {
      t.join();
    }

    System.out.println(updater.get(a));
    System.out.println(threadNum == updater.get(a));
  }

}
