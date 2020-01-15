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

package com.github.flysium.io.photon.juc.c300_threadgroup;

/**
 * 线程组批量停止线程
 *
 * @author Sven Augustus
 */
public class T01_GroupStopTest {

  public static void main(String[] args) {
    Runnable r = () -> {
      System.out.println("ThreadName=" + Thread.currentThread().getName() + "准备开始死循环了：)");
      while (!Thread.currentThread().isInterrupted()) {
      }
      System.out.println("ThreadName=" + Thread.currentThread().getName() + "结束了：)");
    };
    ThreadGroup group = new ThreadGroup("我的线程组");
    for (int i = 0; i < 5; i++) {
      new Thread(group, r, "线程" + (i + 1)).start();
    }
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    group.interrupt();
    System.out.println("调用了interrupt()方法");
  }

}
