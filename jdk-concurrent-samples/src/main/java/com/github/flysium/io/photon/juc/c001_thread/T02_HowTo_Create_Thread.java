
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

package com.github.flysium.io.photon.juc.c001_thread;

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

