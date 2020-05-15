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

import java.time.LocalDateTime;

/**
 * 采用 sleep, 不释放锁; 采用 t0.join, 可以释放t0对象锁
 *
 * @author Sven Augustus
 */
public class Test_sleep_vs_join {

  // 当释放锁时，t2 进入 testLock 方法 可以在 t0 结束前完成
  // 方式1 Thread.sleep(6000) :
  //  2020-01-06T18:36:22.861 ------- T0 begin ...
  //  2020-01-06T18:36:23.808 T2 准备启动
  //  2020-01-06T18:36:27.862 ------- T0 end !
  //  2020-01-06T18:36:28.809 T2 获得t0对象锁，进入       (可以看到T2需要等待5秒(T1早启动1秒,但睡眠了6秒)过后才能获得锁)
  // 方式2 t0.join(6000) :
  //  2020-01-06T18:35:41.330 ------- T0 begin ...
  //  2020-01-06T18:35:42.256 T2 准备启动
  //  2020-01-06T18:35:42.256 T2 获得t0对象锁，进入       (可以看到几乎启动成功, 就可以获得锁)
  //  2020-01-06T18:35:46.330 ------- T0 end !

  private static class MyThread extends Thread {

    @Override
    public void run() {
      System.out.println(LocalDateTime.now() + " ------- T0 begin ...");
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(LocalDateTime.now() + " ------- T0 end !");
    }

    public synchronized void testLock() {
      System.out.println(LocalDateTime.now() + " T2 获得t0对象锁，进入 ");
    }

  }

  public static void main(String[] args) {
    final MyThread t0 = new MyThread();
    new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (t0) {
          try {
            t0.start();
            // 1.采用 sleep, 不释放锁
            Thread.sleep(6000);

            // 2.采用 t0.join (本质是 t0.wait(xxx)), 可以释放锁
            // T2 线程在启动后可以获得锁
            // t0.join(6000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println("T1 finished !");
      }
    }).start();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(LocalDateTime.now() + " T2 准备启动 ");
    new Thread(new Runnable() {
      @Override
      public void run() {
        t0.testLock();
        System.out.println("T2 finished !");
      }
    }).start();

  }

}
