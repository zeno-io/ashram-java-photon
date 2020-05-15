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

package com.github.flysium.io.photon.juc.c020_Exercises_monitor5;

import java.util.ArrayList;
import java.util.List;

/**
 * 错误示例
 *
 * @author Sven Augustus
 */
public class E03_wait_notify {

  static class MyContainer {

    List list = new ArrayList<>();

    public void add(Object o) {
      list.add(o);
      System.out.println(o);
    }

    public int size() {
      return list.size();
    }
  }

  static MyContainer container = new MyContainer();

  static Object lockObject = new Object();

  public static void main(String[] args) {
    new Thread(() -> {
      synchronized (lockObject) {
        if (container.size() != 5) {
          try {
            //释放锁，让t1得以执行
            lockObject.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println("now is " + container.size());
      }
    }, "t2").start();

    new Thread(() -> {
      synchronized (lockObject) {
        for (int i = 1; i <= 10; i++) {
          container.add(i);
          if (i == 5) {
            lockObject.notify();
          }
        }
      }
    }, "t1").start();
  }

}
