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
 * t.interrupt() , t.interrupted() , t.isInterrupted() 傻傻分不清 ?
 *
 * @author Sven Augustus
 */
public class Test_interrupt_vs_interrupted {

  //  interrupt()仅告诉线程中断标记，并不能真正的中断线程的运行
  //  interrupted() = isInterrupted(ClearInterrupted:true) 测试当前线程是否已经中断。线程的中断状态 由该方法清除。
  //            换句话说，如果连续两次调用该方法，则第二次调用将返回 false（在第一次调用已清除了其中断状态之后， 且第二次调用检验完中断状态前，当前线程再次中断的情况除外）。
  //  isInterrupted() = isInterrupted(ClearInterrupted:false) 测试线程是否已经中断状态。线程的中断状态 不受该方法的影响。

  public static void main(String[] args) {
    System.out.println("----------------- Test Interrupt Running Thread -----------------");
    Thread t11 = getRunningThread("T11");
    t11.start();
    //  interrupt()仅告诉线程中断标记，并不能真正的中断线程的运行
    t11.interrupt();
    System.out.println("T11 是否处于中断状态？=" + t11.isInterrupted());
    waitForEnd(t11);
    System.out.println("T11 是否处于中断状态？=" + t11.isInterrupted());

    System.out.println("----------------- Test Interrupt Sleep Thread -----------------");
    Thread t12 = getSleepThread("T12");
    t12.start();
    //  interrupt()仅告诉线程中断标记，并不能真正的中断线程的运行
    t12.interrupt();
    System.out.println("T12 是否处于中断状态？=" + t12.isInterrupted());
    waitForEnd(t12);
    System.out.println("T12 是否处于中断状态？=" + t12.isInterrupted());

    System.out.println("----------------- Test Interrupted -----------------");
    //  interrupted() 测试当前线程是否已经中断。线程的中断状态 由该方法清除。
    System.out.println("interrupted 是否处于中断状态？=" + Thread.interrupted());
    System.out.println("interrupted 是否处于中断状态？=" + Thread.interrupted());
  }

  private final static int LEN = 100000;

  private static Thread getRunningThread(String name) {
    return new Thread(() -> {
      int sum = 0;
      for (int i = 0; i < LEN; i++) {
        sum += i;
      }
      System.out.println(Thread.currentThread().getName() + " finished !, sum = " + sum);
    }, name);
  }

  private static Thread getSleepThread(String name) {
    return new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        System.out.println(Thread.currentThread().getName() + " " + e);
      }
      System.out.println(Thread.currentThread().getName() + " finished ! ");
    }, name);
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
