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

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * synchronized 的三种方式
 *
 * @author Sven Augustus
 */
public class T02_synchronizedWays {

  // synchronized 的三种方式:
  // （1）synchronized方法  与  synchronized(this) 代码块   ------锁定的都是当前对象，不同的只是同步代码的范围
  // （2）synchronized (非this对象x)  将对象x本身作为“对象监视器”
  //   a、多个线程同时执行 synchronized(x) 代码块，呈现同步效果。
  //   b、当其他线程同时执行对象x里面的 synchronized方法时，呈现同步效果。
  //   c、当其他线程同时执行对象x里面的 synchronized(this)方法时，呈现同步效果。
  // （3）静态synchronized方法 与  synchronized(calss)代码块   ------锁定的都是Class对象。Class 锁与 对象锁 不是同一个锁，两者同时使用情况可能呈异步效果。

  private void testLockThis() {
    synchronized (this) {
      System.out
          .println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " testLockThis");
      sleepAWhile();
    }
  }

  private synchronized void testLockMethod() {
    System.out
        .println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " testLockMethod");
    sleepAWhile();
  }

  private static void testLockObject(Object o) {
    synchronized (o) {
      System.out.println(
          LocalDateTime.now() + " " + Thread.currentThread().getName() + " testLockObject");
      sleepAWhile();
    }
  }

  private static synchronized void testLockClass() {
    System.out
        .println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " testLockClass");
    sleepAWhile();
  }

  public static void main(String[] args) {
    final T02_synchronizedWays o = new T02_synchronizedWays();
    new Thread(o::testLockThis, "T11").start();
    new Thread(o::testLockMethod, "T12").start();
    new Thread(() -> testLockObject(o), "T21").start();
    new Thread(T02_synchronizedWays::testLockClass, "T31").start();
  }

  private static void sleepAWhile() {
    try {
      TimeUnit.MILLISECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
