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
public class E01_volatile {

  static class MyContainer {

    volatile List list = new ArrayList<>();

    public void add(Object o) {
      list.add(o);
      System.out.println(o);
    }

    public int size() {
      return list.size();
    }
  }

  volatile static MyContainer container = new MyContainer();

  public static void main(String[] args) {
    new Thread(() -> {
      for (int i = 1; i <= 10; i++) {
        container.add(i);
      }
    }, "t1").start();

    new Thread(() -> {
      while (container.size() != 5) {
      }
      System.out.println("now is 5");
    }, "t2").start();
  }

}
