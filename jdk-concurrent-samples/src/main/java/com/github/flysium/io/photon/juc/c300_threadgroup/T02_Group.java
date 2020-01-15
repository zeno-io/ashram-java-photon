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
 * 线程组测试
 *
 * @author Sven Augustus
 */
public class T02_Group {

  public static void main(String[] args) throws InterruptedException {
    Runnable r = () -> {
      try {
        while (!Thread.currentThread().isInterrupted()) {
          System.out.println("ThreadName=" + Thread.currentThread().getName());
          Thread.sleep(3000);
        }
      } catch (InterruptedException e) {
        // e.printStackTrace();
      }
    };
    ThreadGroup group = new ThreadGroup("sven's thread group");
    Thread t1 = new Thread(group, r, "t1");
    Thread t2 = new Thread(group, r, "t2");
    t1.start();
    t2.start();
    Thread.sleep(1000);
    System.out.println("线程组的活动线程数：" + group.activeCount());
    System.out.println("线程组的名称：" + group.getName());
    Thread.sleep(10000);
    t1.interrupt();
    t2.interrupt();
  }

}
