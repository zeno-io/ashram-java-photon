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
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 采用 CyclicBarrier 简单模拟几个游客一起约旅游几个景点
 *
 * @author Sven Augustus
 */
public class T04_Phaser_Vs_CyclicBarrier {

  public static void main(String[] args) {
    // 定义游客
    final String[] tourists = new String[]{"小红", "小陈", "小黄", "小白", "小青", "小蓝", "小紫"};
    final String[] points = new String[]{"出发点", "景点A", "景点B", "景点C", "酒店", "飞机场，回家去"};

    // 创建时，就需要指定参与的parties个数
    CyclicBarrier barrier = new CyclicBarrier(tourists.length, () -> {
      System.out.println("咱们人齐了，走");
    });

    for (String tourist : tourists) {
      new Thread(() -> {
        for (String point : points) {
          goToPoint(barrier, point);
        }
      }, tourist).start();
    }
  }

  private static void goToPoint(CyclicBarrier barrier, String point) {
    try {
      randomSleep(point);
      // 开始等待其他线程
      barrier.await();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void randomSleep(String point) {
    Random random = new Random();
    try {
      final long time = 100 + random.nextInt(900);
      TimeUnit.MILLISECONDS.sleep(time);
      System.out.println(Thread.currentThread().getName() + " 花了 " + time + " 时间才到了" + point);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
