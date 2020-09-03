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
