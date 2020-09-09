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

package xyz.flysium.photon.c001_thread;

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

  public static void main(String[] args) throws InterruptedException {
    System.out.println("----------------- Test Interrupt Running Thread -----------------");
    Thread t11 = getRunningThread("T11");
    t11.start();
    //  interrupt()仅告诉线程中断标记，并不能真正的中断线程的运行
    TimeUnit.MILLISECONDS.sleep(50);
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
      int k = 0;
      for (int i = 0; i < LEN; i++) {
        k = i;
        sum += i;
        try {
          TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
          break;
        }
      }
      System.out.println(Thread.currentThread().getName()
          + " finished !, sum = " + sum + ", k = " + k);
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
