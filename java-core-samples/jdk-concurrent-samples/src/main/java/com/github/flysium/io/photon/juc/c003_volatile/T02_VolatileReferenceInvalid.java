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

package com.github.flysium.io.photon.juc.c003_volatile;

import java.util.concurrent.TimeUnit;

/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 *
 * @author Sven Augustus
 */
public class T02_VolatileReferenceInvalid {

  private static class RefObj {

    private Boolean running = false;

    public void m() {
      System.out.println(Thread.currentThread().getName() + " begin... ");
      while (running) {
//        try {
//          TimeUnit.MILLISECONDS.sleep(1);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
      }
      System.out.println(Thread.currentThread().getName() + " end !");
    }

  }

  volatile static RefObj refObj = new RefObj();

  public static void main(String[] args) {
    refObj.running = true;
    // 方式一
    new Thread(() -> {
      refObj.m();
    }, "T1").start();
    // 方式二：思考为什么下面这种能读到？？
    //  CPU高速缓存 与 主存 是会同步的。volatile 保证写之后，一定同步到主存。
    //  没有 volatile 只是写之后不立刻同步，但某个时间还是会同步。（这个时间其实对于人来说很短，对于CPU来说慢）
//    new Thread(() -> {
//      System.out.println(Thread.currentThread().getName() + " begin... ");
//      while (refObj.running) {
//      }
//      System.out.println(Thread.currentThread().getName() + " end !");
//    }, "T2").start();
    try {
//      TimeUnit.MILLISECONDS.sleep(1);
      // 设置 1000秒,为了减少 高速缓存与主存同步的时间差
      // 睡眠时间短或者 m 中睡眠一些时间，就可以触发 高速缓存与主存同步， 但是不稳定
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    refObj.running = false;
  }

}

