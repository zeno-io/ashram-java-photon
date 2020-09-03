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
