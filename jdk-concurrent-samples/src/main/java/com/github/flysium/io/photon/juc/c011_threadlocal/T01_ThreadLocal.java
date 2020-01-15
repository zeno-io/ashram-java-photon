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

package com.github.flysium.io.photon.juc.c011_threadlocal;

import java.util.Date;

/**
 * ThreadLocal的隔离性
 *
 * @author Sven Augustus
 */
public class T01_ThreadLocal {

  static ThreadLocal<Date> tl = new ThreadLocal<Date>() {

    /**
     * 初始化ThreadLocal变量，解决get()返回null问题
     */
    @Override
    protected Date initialValue() {
      return new Date();
    }

  };

  public static void main(String[] args) throws InterruptedException {
    Runnable r = () -> {
      try {
        for (int i = 0; i < 20; i++) {
          /*
           * if (Tools.tl.get() == null) { Tools.tl.set(new Date()); }
           */
          System.out.println(Thread.currentThread().getName() + " " + tl.get().getTime());
          Thread.sleep(100);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    Thread a = new Thread(r, "A");
    a.start();

    Thread.sleep(1000);

    Thread b = new Thread(r, "B");
    b.start();

    a.join();
    b.join();
    tl.remove();
  }

}
