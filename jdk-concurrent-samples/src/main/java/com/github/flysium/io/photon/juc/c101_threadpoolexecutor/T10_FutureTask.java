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

package com.github.flysium.io.photon.juc.c101_threadpoolexecutor;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask类是Future 的一个实现，并实现了Runnable
 *
 * @author Sven Augustus
 */
public class T10_FutureTask {

  public static void main(String[] args) {
    // 初始化一个Callable对象和FutureTask对象
    FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> {
      Thread.sleep(2000);
      int totalMoney = new Random().nextInt(10000);
      System.out.println("您当前有" + totalMoney + "在您的私有账户中");
      return totalMoney;
    });
    long startTimeMillis = System.currentTimeMillis();
    System.out.println("futureTask线程现在开始启动，启动时间为：" + new java.util.Date(startTimeMillis));

    new Thread(futureTask).start();

    System.out.println("主线程开始执行其他任务");
    // 从其他账户获取总金额
    int totalMoney = 1000;
    System.out.println("现在你在其他账户中的总金额为" + totalMoney);
    System.out.println("等待私有账户总金额统计完毕...");

    // 测试后台的计算线程是否完成，如果未完成则等待
    while (!futureTask.isDone()) {
      try {
        Thread.sleep(500);
        System.out.println("私有账户计算未完成继续等待...");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    long endTimeMillis = System.currentTimeMillis();
    System.out.println("futureTask线程计算完毕，此时时间为" + new java.util.Date(endTimeMillis) + ",耗时："
        + (endTimeMillis - startTimeMillis) + "ms");

    Integer money = null;
    try {
      money = (Integer) futureTask.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    System.out.println("您现在的总金额为：" + (totalMoney + money));
  }

}
