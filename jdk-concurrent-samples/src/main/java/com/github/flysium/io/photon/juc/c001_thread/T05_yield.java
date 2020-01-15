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
 * Thread.yield(); 放弃当前线程的CPU资源。放弃时间不确认，也有可能刚刚放弃又获得CPU资源。
 *
 * @author Sven Augustus
 */
public class T05_yield {

  // T1 每打印3次， 放弃CPU资源一次
  // 同样的耗时工作量，有可能导致T2领先完成（并不能完全保证）

  public static void main(String[] args) {
    new Thread(() -> {
      for (int i = 0; i < 50; i++) {
        try {
          TimeUnit.MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("T1 " + i);
        // T1 每打印3次， 放弃CPU资源一次
        if (i % 3 == 0) {
          Thread.yield();
        }
      }
      System.out.println("T1 finished !");
    }).start();

    new Thread(() -> {
      for (int i = 0; i < 50; i++) {
        try {
          TimeUnit.MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("-------------T2 " + i);
      }
      System.out.println("T2 finished !");
    }).start();
  }

}
