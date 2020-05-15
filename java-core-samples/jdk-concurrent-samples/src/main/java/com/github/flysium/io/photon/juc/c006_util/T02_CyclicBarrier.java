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

package com.github.flysium.io.photon.juc.c006_util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier循环栅栏
 *
 * @author Sven Augustus
 */
public class T02_CyclicBarrier {

  // CyclicBarrier与CountDownLatch的区别：
  // CountDownLatch 强调有 若干个线程 等待 (await) 参与计数的线程 完成某件事情（它们完成时调用计数 countDown），只能用一次，无法重置
  // CyclicBarrier 强调的是 多个线程互相等待完成 (await)，才继续，可以重置。

  //  await() 等待其它参与方的到来（调用await()）。
  //         （1）如果当前调用是最后一个调用，则唤醒所有其它的线程的等待。
  //          并且如果在构造CyclicBarrier时指定了action，当前线程会去执行该action。
  //          该方法返回该线程调用await的次序（getParties()-1 说明该线程是第一个调用await的，0 说明该线程是最后一个执行await的），
  //          接着该线程继续执行await后的代码；
  //          （2）如果该调用不是最后一个调用，则阻塞等待；
  //          （3）如果等待过程中，当前线程被中断，则抛出InterruptedException；
  //          （4）如果等待过程中，其它等待的线程被中断，或者其它线程等待超时，或者该barrier被reset，
  //                  或者当前线程在执行barrier构造时注册的action时因为抛出异常而失败，则抛出BrokenBarrierException。
  //  await(long timeout, TimeUnit unit) 与await()唯一的不同点在于设置了等待超时时间，等待超时时会抛出TimeoutException。
  //  reset() 该方法会将该barrier重置为它的初始状态，并使得所有对该barrier的await调用抛出BrokenBarrierException。

  // 创建时，就需要指定参与的parties个数
  static CyclicBarrier barrier = new CyclicBarrier(10, () -> {
    System.out.println(Thread.currentThread().getName() + " 满人了，我是最后一个到！");
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("大家可以继续了");
  });

  public static void main(String[] args) {
    for (int i = 0; i < 20; i++) {
      new Thread(() -> {
//        System.out.println(Thread.currentThread().getName() + " 来了");
        try {
          // 如果所有的parties都到达，则barrier 会重置， 开启新的一次周期（generation）
          int index = barrier.await();
//          System.out.println(Thread.currentThread().getName()
//              + " 我是第" + (barrier.getParties() - index) + "个");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 冲啊！");
      }, "t" + i).start();
    }
  }

}
