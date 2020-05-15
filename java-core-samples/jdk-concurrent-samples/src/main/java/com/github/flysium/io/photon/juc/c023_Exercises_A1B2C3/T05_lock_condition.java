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

package com.github.flysium.io.photon.juc.c023_Exercises_A1B2C3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock + condition
 *
 * @author Sven Augustus
 */
public class T05_lock_condition {

  static Lock lock = new ReentrantLock();
  static Condition charPrint = lock.newCondition();
  static Condition intPrint = lock.newCondition();

  public static void main(String[] args) {
    new Thread(() -> {
      lock.lock();
      try {
        for (int i = 0; i < 26; i++) {
          try {
            intPrint.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.print((i + 1));
          charPrint.signal();
        }
        charPrint.signal();
      } finally {
        lock.unlock();
      }
    }, "INTS").start();

    new Thread(() -> {
      lock.lock();
      try {
        for (int i = 0; i < 26; i++) {
          System.out.print((char) ('A' + i));
          intPrint.signal();
          try {
            charPrint.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        intPrint.signal();
      } finally {
        lock.unlock();
      }
    }, "CHARS").start();

  }

}
