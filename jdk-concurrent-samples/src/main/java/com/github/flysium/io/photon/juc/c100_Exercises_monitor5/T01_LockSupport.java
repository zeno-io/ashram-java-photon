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
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport
 *
 * @author Sven Augustus
 */
public class T01_LockSupport {

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
  static Thread t1, t2;

  public static void main(String[] args) {
    t2 = new Thread(() -> {
      LockSupport.park();
      System.out.println("now is " + container.size());
      LockSupport.unpark(t1);
    }, "t2");

    t1 = new Thread(() -> {
      for (int i = 1; i <= 10; i++) {
        container.add(i);
        if (i == 5) {
          LockSupport.unpark(t2);
          LockSupport.park();
        }
      }
    }, "t1");

    t1.start();
    t2.start();
  }

}
