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
