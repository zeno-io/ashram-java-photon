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

package com.github.flysium.io.photon.juc.c002_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * synchronized 什么时候释放锁?
 *
 * @author Sven Augustus
 */
public class T05_WhenTo_Release_Lock {

  // synchronized 什么时候释放锁?
  // （1）当线程的同步方法、同步代码库执行结束，就可以释放同步监视器
  // （2）当线程在同步代码库、方法中遇到 break、return 终止代码的运行，也可释放
  // （3）当线程在同步代码库、 同步方法中遇到未处理的 Error、Exception，导致该代码结束也可释放同步监视器
  // （4）当线程在同步代码库、同步方法中，程序执行了同步监视器对象的wait方法，导致方法暂停，释放同步监视器 .

  private int num = 0;

  private synchronized void synchronizedMethod() {
    System.out.println(Thread.currentThread().getName() + " synchronizedMethod");
    if ("T1".equals(Thread.currentThread().getName())) {
      // （2）当线程在同步代码库、方法中遇到 break、return 终止代码的运行，也可释放
      return;
    }
    if ("T2".equals(Thread.currentThread().getName())) {
      // （3）当线程在同步代码库、 同步方法中遇到未处理的 Error、Exception，导致该代码结束也可释放同步监视器
      throw new RuntimeException("T2 exception !");
    }
    if ("T3".equals(Thread.currentThread().getName())) {
      // （4）当线程在同步代码库、同步方法中，程序执行了同步监视器对象的wait方法，导致方法暂停，释放同步监视器 .
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    sleepAWhile();
    num++;
    System.out.println(Thread.currentThread().getName() + " num=" + num);
    // （1）当线程的同步方法、同步代码库执行结束，就可以释放同步监视器
  }

  public static void main(String[] args) {
    final T05_WhenTo_Release_Lock o = new T05_WhenTo_Release_Lock();
    new Thread(o::synchronizedMethod, "T1").start();
    new Thread(o::synchronizedMethod, "T2").start();
    new Thread(o::synchronizedMethod, "T3").start();
    new Thread(o::synchronizedMethod, "T4").start();
    new Thread(o::synchronizedMethod, "T5").start();
  }

  private static void sleepAWhile() {
    try {
      TimeUnit.MILLISECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
