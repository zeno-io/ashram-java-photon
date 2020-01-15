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

package com.github.flysium.io.photon.juc.c100_Exercises_monitor5;

import java.util.ArrayList;
import java.util.List;

/**
 * synchronized + notify / wait
 *
 * @author Sven Augustus
 */
public class T02_wait_notify {

  static class MyContainer {

    /*volatile*/ List list = new ArrayList<>();

    public /*synchronized*/  void add(Object o) {
      list.add(o);
      System.out.println(o);
    }

    public /*synchronized*/ int size() {
      return list.size();
    }
  }

  /*volatile*/ static MyContainer container = new MyContainer();

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
        lockObject.notify();
      }
    }, "t2").start();

    new Thread(() -> {
      synchronized (lockObject) {
        for (int i = 1; i <= 10; i++) {
          container.add(i);
          if (i == 5) {
            lockObject.notify();
            try {
              //释放锁，让t2得以执行
              lockObject.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }, "t1").start();

  }

}
