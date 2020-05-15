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

package com.github.flysium.io.photon.juc.c001_thread;

import java.util.concurrent.TimeUnit;

/**
 * t.join() 等待该线程t 销毁终止。
 *
 * @author Sven Augustus
 */
public class T06_join {

  // 使用 join 将 t1 、t2、t3 按顺序执行了

  public static void main(String[] args) {
    Thread t1 = new Thread(() -> {
      try {
        TimeUnit.MILLISECONDS.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("T1 finished !");
    });

    Thread t2 = new Thread(() -> {
      // t2 等待 t1 执行完毕
      try {
        t1.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("T2 finished !");
    });

    Thread t3 = new Thread(() -> {
      // t3 等待 t2 执行完毕
      try {
        t2.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("T3 finished !");
    });

    t1.start();
    t2.start();
    t3.start();
  }

}
