
/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.juc.c002_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * 应该避免将锁定对象的引用变成另外的对象
 *
 * @author Sven Augustus
 */
public class T07__SyncSameObject {

  // 锁定某对象o，如果o的属性发生改变，不影响锁的使用
  // 但是如果o变成另外一个对象，则锁定的对象发生改变

  /*final*/
  Object o = new Object();


  void m() {
    synchronized (o) {
      while (true) {
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
      }
    }
  }

  public static void main(String[] args) {
    T07__SyncSameObject t = new T07__SyncSameObject();
    // 启动第一个线程
    new Thread(t::m, "t1").start();

    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // 创建第二个线程
    Thread t2 = new Thread(t::m, "t2");

    // 锁对象发生改变，所以t2线程得以执行，如果注释掉这句话，线程2将永远得不到执行机会
    t.o = new Object();

    t2.start();
  }

}
