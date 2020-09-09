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
 * 什么是线程？
 *
 * @author Sven Augustus
 */
public class T01_WhatIsThread {

  //  线程是进程中某一个连续控制流程/执行路径。
  //  在一个进程中可以拥有多个并行的线程。
  // 以下结果中出现了两个并行的执行路径： main 线程 和 Thread-0 线程 ，呈现的结果是交替输出信息到控制台

  public static void main(String[] args) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 5; i++) {
          try {
            TimeUnit.MILLISECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println(Thread.currentThread().getName());
        }
      }
    }).start();
    for (int i = 0; i < 5; i++) {
      try {
        TimeUnit.MILLISECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName());
    }
  }

}
