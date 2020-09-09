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
 * Thread.yield(); 放弃当前线程的CPU资源。放弃时间不确认，也有可能刚刚放弃又获得CPU资源。
 *
 * @author Sven Augustus
 */
public class T05_yield {

  // T1 每打印3次， 放弃CPU资源一次
  // 同样的耗时工作量，有可能导致T2领先完成（并不能完全保证）

  public static void main(String[] args) {
    new Thread(() -> {
      for (int i = 0; i < 50; i++) {
        try {
          TimeUnit.MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("T1 " + i);
        // T1 每打印3次， 放弃CPU资源一次
        if (i % 3 == 0) {
          Thread.yield();
        }
      }
      System.out.println("T1 finished !");
    }).start();

    new Thread(() -> {
      for (int i = 0; i < 50; i++) {
        try {
          TimeUnit.MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("-------------T2 " + i);
      }
      System.out.println("T2 finished !");
    }).start();
  }

}
