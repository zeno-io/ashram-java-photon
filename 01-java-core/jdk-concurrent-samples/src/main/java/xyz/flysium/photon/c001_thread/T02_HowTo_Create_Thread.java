
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

import java.util.concurrent.Executors;

/**
 * 如何创建一个线程？
 *
 * @author Sven Augustus
 */
public class T02_HowTo_Create_Thread {

  // 创建线程的方式
  // 方式1：继承 Thread 类
  // 方法2：实现 Runnable 接口
  // 方法3：使用线程池进行提交任务

  static class MyThread extends Thread {

    @Override
    public void run() {
      System.out.println(Thread.currentThread().getName() + "-MyThread");
    }
  }

  static class MyRunnable implements Runnable {

    @Override
    public void run() {
      System.out.println(Thread.currentThread().getName() + "-MyRunnable");
    }
  }

  public static void main(String[] args) {
    // 方式1：继承 Thread 类
    new MyThread().start();
    // 方法2：实现 Runnable 接口
    new Thread(new MyRunnable()).start();
    // 方法3：使用线程池进行提交任务
    Executors.newSingleThreadExecutor().submit(() -> {
      System.out.println(Thread.currentThread().getName() + "-Executors");
    });
  }

}

