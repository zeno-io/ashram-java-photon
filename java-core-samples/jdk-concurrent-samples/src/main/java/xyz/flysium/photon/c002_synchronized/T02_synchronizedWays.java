/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c002_synchronized;

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
