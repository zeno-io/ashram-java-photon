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
 * 如何停止一个线程?
 *
 * @author Sven Augustus
 */
public class T07_Try_Stop_Thread {

  // 停止线程的方式
  // 方式1：interrupt() +return
  // 方法2：interrupt() +抛异常
  // 方法3：stop() ---- 不推荐

  public static void main(String[] args) {
    System.out.println("----------------- interrupt() + return -----------------");
    // 方式1：interrupt() +return
    Thread t1 = new Thread(() -> {
      int sum = 0;
      final int LEN = 10000;
      for (int i = 0; i < LEN; i++) {
        if (Thread.currentThread().isInterrupted()) {
          // return
          System.out.println("T1 end ! ");
          return;
        }
        sum += i;
        if (i % 5 == 0) {
          try {
            TimeUnit.MILLISECONDS.sleep(1);
          } catch (InterruptedException e) {
            // return
            System.out.println("T1 end ! ");
            return;
          }
        }
      }
      System.out.println("T1 finished ! ");
    });
    t1.start();
    t1.interrupt();
    waitForEnd(t1);

    System.out.println("----------------- interrupt() + 抛异常 -----------------");
    // 方法2：interrupt() +抛异常
    Thread t2 = new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        throw new RuntimeException("T2 end !" + e);
      }
      System.out.println("T2 finished ! ");
    });
    t2.start();
    t2.interrupt();
    waitForEnd(t2);

    System.out.println("----------------- stop() -----------------");
    // 方法3：stop() ---- 不推荐
    Thread t31 = new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        System.out.println("T3 end !" + e);
      }
      System.out.println("T3 finished ! ");
    });
    t31.start();
    t31.stop();
    waitForEnd(t31);

    System.out.println("----------------- stop() -----------------");
    // 方法3：stop() ---- 不推荐, 可能导致数据不一致
    final SyncObject lockObject = new SyncObject();
    Thread t32 = new Thread(() -> {
      lockObject.inject("root", "123456");
      System.out.println("T3 finished ! ");
    });
    t32.start();
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t32.stop();
    System.out.println("lockObject = " + lockObject);
    waitForEnd(t32);
    System.out.println("lockObject = " + lockObject);
  }

  private static class SyncObject {

    private String name;
    private String pwd;

    public synchronized void inject(String name, String pwd) {
      this.name = name;
      try {
        TimeUnit.SECONDS.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      this.pwd = pwd;
    }

    @Override
    public String toString() {
      return "SyncObject{" +
          "name='" + name + '\'' +
          ", pwd='" + pwd + '\'' +
          '}';
    }
  }

  private static void waitForEnd(Thread t1) {
    // wait for end.
    try {
      t1.join();
    } catch (InterruptedException e) {
      // ignore
    }
  }

}
