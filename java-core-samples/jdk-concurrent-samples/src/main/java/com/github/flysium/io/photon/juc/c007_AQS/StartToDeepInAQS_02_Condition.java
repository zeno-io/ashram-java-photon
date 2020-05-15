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

package com.github.flysium.io.photon.juc.c007_AQS;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 深入 AbstractQueuedSynchronizer 源码
 *
 * @author Sven Augustus
 */
public class StartToDeepInAQS_02_Condition {

  static ReentrantLock lock = new ReentrantLock(true);
  static Condition c1 = lock.newCondition();
  static Condition c2 = lock.newCondition();

  public static void main(String[] args) {
    new Thread(() -> {
      lock.lock();
      try {
        for (int i = 0; i < 3; i++) {
          System.out.println(Thread.currentThread().getName() + " " + i);
          c2.signal();
          c1.await();
        }
        c2.signal();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }, "A").start();

    new Thread(() -> {
      lock.lock();
      try {
        for (int i = 0; i < 3; i++) {
          System.out.println(Thread.currentThread().getName() + " " + i);
          c1.signal();
          c2.await();
        }
        c1.signal();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }, "B").start();
  }

}
