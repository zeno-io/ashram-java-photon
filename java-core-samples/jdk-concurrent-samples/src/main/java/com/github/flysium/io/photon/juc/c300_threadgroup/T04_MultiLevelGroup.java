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
 * 线程组多级关联
 *
 * @author Sven Augustus
 */
public class T04_MultiLevelGroup {

  public static void main(String[] args) throws InterruptedException {
    ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
    ThreadGroup group = new ThreadGroup(mainGroup, "组A");

    new Thread(group, (Runnable) () -> {
      System.out.println("run");
      // 线程必须在运行状态才可以受组管理
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "线程Z").start();

    System.out.println("main线程组的子线程组个数：" + mainGroup.activeGroupCount() + ",线程数：" + mainGroup
        .activeCount());
    ThreadGroup[] listGroup = new ThreadGroup[Thread.currentThread().getThreadGroup()
        .activeGroupCount()];
    Thread.currentThread().getThreadGroup().enumerate(listGroup);
    System.out
        .println("main线程中有多少个子线程组：" + listGroup.length + " 名字为：" + listGroup[0].getName());
    Thread[] listThread = new Thread[listGroup[0].activeCount()];
    listGroup[0].enumerate(listThread);
    System.out.println("子线程组的线程：" + listThread[0].getName());
  }

}
