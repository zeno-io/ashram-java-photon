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

package xyz.flysium.photon.c006_util;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 倒数计数器
 *
 * @author Sven Augustus
 */
public class T01_CountDownLatch {

  //（1）作为启动信号：将计数 1 初始化的 CountDownLatch 用作一个简单的开/关锁存器，或入口。
  //（2）作为结束信号：在通过调用 countDown() 的线程打开入口前，所有调用 await 的线程都一直在入口处等待。
  //  用 N 初始化的 CountDownLatch 可以使一个线程在 N 个线程完成某项操作之前一直等待，或者使其在某项操作完成 N 次之前一直等待。

  //  countDown()
  //  （1）如果当前计数器的值大于1，则将其减1；
  //  （2）若当前值为1，则将其置为0并唤醒所有通过await等待的线程；
  //  （3）若当前值为0，则什么也不做直接返回。
  //  await() 等待计数器的值为0，若计数器的值为0则该方法返回；若等待期间该线程被中断，则抛出InterruptedException并清除该线程的中断状态。
  //  await(long timeout, TimeUnit unit)
  //  （1）在指定的时间内等待计数器的值为0，若在指定时间内计数器的值变为0，则该方法返回true；
  //  （2）若指定时间内计数器的值仍未变为0，则返回false；
  //  （3）若指定时间内计数器的值变为0之前当前线程被中断，则抛出InterruptedException并清除该线程的中断状态。
  //  getCount() 读取当前计数器的值，一般用于调试或者测试。

  // 计算同时跑多个相同任务，结束需要的耗时时间
  public static long timeTasks(int nThreads, Runnable runnable) throws InterruptedException {
    // 创建时，就需要指定参与的parties个数
    CountDownLatch startSignal = new CountDownLatch(1);
    CountDownLatch doneSignal = new CountDownLatch(nThreads);
    for (int i = 0; i < nThreads; i++) {
      new Thread(() -> {
        try {
          // 阻塞于此，一直到startSignal计数为0，再往下执行
          startSignal.await();
          try {
            runnable.run();
          } finally {
            //  doneSignal 计数减一，直到最后一个线程结束
            doneSignal.countDown();
          }
        } catch (InterruptedException e) {

        }
      }, "task-" + i).start();
    }
    long start = System.currentTimeMillis();
    // doneSignal 计数减一，为0，所有task开始并发执行run
    startSignal.countDown();
    // 阻塞于此，一直到doneSignal计数为0，再往下执行
    doneSignal.await();
    return System.currentTimeMillis() - start;
  }

  public static void main(String[] args) throws InterruptedException {
    Random random = new Random();
    long time = timeTasks(10, () -> {
      try {
        Thread.sleep(random.nextInt(1000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName() + " end");
    });
    System.out.println("耗时：" + time + "ms");
  }

}
