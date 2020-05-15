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

/**
 * synchronized + notify / wait
 *
 * @author Sven Augustus
 */
public class T04_wait_notify {

  static final Object lockObject = new Object();

  public static void main(String[] args) {
    new Thread(() -> {
      synchronized (lockObject) {
        for (int i = 0; i < 26; i++) {
          try {
            lockObject.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.print((i + 1));
          lockObject.notify();
        }
        lockObject.notify();
      }
    }, "INTS").start();

    new Thread(() -> {
      synchronized (lockObject) {
        for (int i = 0; i < 26; i++) {
          System.out.print((char) ('A' + i));
          lockObject.notify();
          try {
            lockObject.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        lockObject.notify();
      }
    }, "CHARS").start();
  }

}
