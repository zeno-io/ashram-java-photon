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
 * suspend() 与resume() 独占同步对象, 导致数据不一致。
 *
 * @author Sven Augustus
 */
public class Test_suspend_resume {

  public static void main(String[] args) {
    final SyncObject lockObject = new SyncObject();
    Thread t1 = new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + " begin...");
      lockObject.testLock();
      System.out.println(Thread.currentThread().getName() + " end!");
    });
    t1.start();
    t1.setName(SUSPEND_THREAD);

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Thread t2 = new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + " begin...");
      lockObject.testLock();
      System.out.println(Thread.currentThread().getName() + " end!");
    });
    t2.start();
    //   t1.resume();
  }

  private static final String SUSPEND_THREAD = "suspendThread";

  private static class SyncObject {

    public synchronized void testLock() {
      System.out.println(Thread.currentThread().getName() + " testLock begin...");
      if (SUSPEND_THREAD.equals(Thread.currentThread().getName())) {
        System.out.println(Thread.currentThread().getName() + " prepare to suspend...");
        Thread.currentThread().suspend();
      }
      System.out.println(Thread.currentThread().getName() + " testLock end");
    }
  }

}
