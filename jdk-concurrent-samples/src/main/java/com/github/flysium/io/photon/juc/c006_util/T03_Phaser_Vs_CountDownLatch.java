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

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 采用 CountDownLatch 简单模拟乘务员需要等待所有的乘客都下飞机了才一起去清洁打扫飞机
 *
 * @author Sven Augustus
 */
public class T03_Phaser_Vs_CountDownLatch {

  public static void main(String[] args) {
    // 定义乘客、乘务员
    final String[] passengers = new String[]{"小红", "小陈", "小黄", "小白", "小青", "小蓝", "小紫"};
    final String[] attendants = new String[]{"阿辉", "阿虎", "阿慧"};

    // 创建时，就需要指定参与的parties个数
    CountDownLatch latch = new CountDownLatch(passengers.length);

    for (String passenger : passengers) {
      new Thread(() -> {
        System.out.println(Thread.currentThread().getName() + " 休息中...");
        randomSleep();
        System.out.println(Thread.currentThread().getName() + " 下飞机！");
        // 倒数计数减一
        latch.countDown();
      }, passenger).start();
    }

    for (String attendant : attendants) {
      new Thread(() -> {
        try {
          // 等待所有的parties都到达
          latch.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 清洁飞机！");
      }, attendant).start();
    }

  }

  private static void randomSleep() {
    Random random = new Random();
    try {
      TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
