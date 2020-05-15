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

package com.github.flysium.io.photon.juc.c006_util;

import java.util.concurrent.Exchanger;

/**
 * Exchanger 两个线程互相交换数据。
 *
 * @author Sven Augustus
 */
public class T09_Exchanger {

  public static void main(String[] args) {
    Exchanger<String> exchanger = new Exchanger<>();

    new Thread(() -> {
      try {
        String result = exchanger.exchange("t1's object");
        System.out.println(Thread.currentThread().getName() + " " + result);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t1").start();

    new Thread(() -> {
      try {
        String result = exchanger.exchange("t2's object");
        System.out.println(Thread.currentThread().getName() + " " + result);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t2").start();
  }
}
