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

package xyz.flysium.photon.c050_gc;

import java.util.concurrent.TimeUnit;

/**
 * 死锁测试
 *
 * <li>
 * 使用 jps 定位 Java 程序
 * </li>
 * <li>
 * 使用 jstack -l [pid] 打印对应 Java 程序的 堆栈信息
 * </li>
 *
 * @author Sven Augustus
 */
public class T03_DeadLock {

  public static void main(String[] args) {
    final Object o1 = new Object();
    final Object o2 = new Object();
    new Thread(() -> {
      synchronized (o1) {
        System.out.println(Thread.currentThread().getName() + " Locked O1...");
        try {
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " Try to Lock O2...");
        synchronized (o2) {
          System.out.println(Thread.currentThread().getName() + " Locked O2...");
        }
      }
    }, "t1").start();
    new Thread(() -> {
      synchronized (o2) {
        System.out.println(Thread.currentThread().getName() + " Locked O2...");

        System.out.println(Thread.currentThread().getName() + " Try to Lock O1...");
        synchronized (o1) {
          System.out.println(Thread.currentThread().getName() + " Locked O1...");
        }
      }
    }, "t2").start();
  }

}
