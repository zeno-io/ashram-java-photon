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

package com.github.flysium.io.photon.juc.c008_ref_and_threadlocal;

import java.util.Date;

/**
 * InheritableThreadLocal 子线程可以读取父线程的值，但反之不行
 *
 * @author Sven Augustus
 */
public class T02_InheritableThreadLocal {

  static InheritableThreadLocal<Date> tl = new InheritableThreadLocal<Date>() {

    @Override
    protected Date initialValue() {
      return new Date();
    }

  };

  private static void read() {
    try {
      for (int i = 0; i < 3; i++) {
        System.out.println(Thread.currentThread().getName() + " " + tl.get().getTime());
        Thread.sleep(100);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws InterruptedException {

    Thread a = new Thread(() -> {
      tl.set(new Date());
      read();
    }, "A");
    a.start();

    Thread.sleep(3000);

    read();

    Thread.sleep(3000);

    Thread b = new Thread(() -> {
      read();
    }, "B");
    b.start();

    a.join();
    b.join();
    tl.remove();
  }


}
