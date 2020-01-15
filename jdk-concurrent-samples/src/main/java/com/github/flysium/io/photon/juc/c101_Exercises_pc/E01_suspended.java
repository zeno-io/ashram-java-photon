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

package com.github.flysium.io.photon.juc.c101_Exercises_pc;

import java.util.LinkedList;

/**
 * 错误示例
 *
 * @author Sven Augustus
 */
public class E01_suspended {

  static class MyContainer {

    /*volatile*/ LinkedList list = new LinkedList();
    private final int max;

    public MyContainer(int max) {
      this.max = max;
    }

    public synchronized void put(Object o) {
      while (getCount() == max) {
      }
      list.addLast(o);
      System.out.println("生产：" + o);
    }

    public synchronized Object get() {
      while (getCount() == 0) {
      }
      return list.removeFirst();
    }

    public /*synchronized*/ int getCount() {
      return list.size();
    }
  }

  /*volatile*/ static MyContainer container = new MyContainer(10);

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        for (int j = 0; j < 2; j++) {
          Object o = container.get();
          System.out.println(Thread.currentThread().getName() + " 消费: " + o);
        }
      }, "c" + i).start();
    }
    for (int i = 0; i < 2; i++) {
      new Thread(() -> {
        for (int j = 0; j < 10; j++) {
          container.put(Thread.currentThread().getName() + " " + j);
        }
      }, "p" + i).start();
    }
  }

}
