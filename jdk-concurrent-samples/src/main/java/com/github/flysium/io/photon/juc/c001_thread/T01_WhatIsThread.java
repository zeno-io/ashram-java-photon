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
