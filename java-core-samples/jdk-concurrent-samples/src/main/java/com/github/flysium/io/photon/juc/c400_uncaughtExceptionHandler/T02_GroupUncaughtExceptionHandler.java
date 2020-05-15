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

package com.github.flysium.io.photon.juc.c400_uncaughtExceptionHandler;

/**
 * UncaughtExceptionHandler支持对线程的异常进行捕捉
 *
 * @author Sven Augustus
 */
public class T02_GroupUncaughtExceptionHandler {

  public static void main(String[] args) throws InterruptedException {
    // 1、线程实例有UncaughtExceptionHandler，仅使用该handler
    // 2、如果线程实例没有UncaughtExceptionHandler，但类有静态handler，则通过静态handler处理
    Runnable r = () -> {
      String username = null;
      System.out.println(username.hashCode());
    };
    Thread t1 = new Thread(r, "t1");
    // 对象
    t1.setUncaughtExceptionHandler((t, e) -> print(e, "对象捕捉--线程："));
    // 类
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> print(e, "静态捕捉--线程："));
    t1.start();
    t1.join();

    System.out.println("---------------------------------------------");

    // 1、线程实例有UncaughtExceptionHandler，仅使用该handler
    // 2.1、如果线程实例没有UncaughtExceptionHandler，但类有静态handler，则先通过静态handler处理
    // 2.2、如果线程实例没有UncaughtExceptionHandler，但线程组有UncaughtExceptionHandler，则后通过线程组的handler处理
    ThreadGroup group = new ThreadGroup("group-a") {

      @Override
      public void uncaughtException(Thread t, Throwable e) {
        super.uncaughtException(t, e);
        print(e, "线程组");
      }
    };
    // 类
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> print(e, "静态捕捉--线程："));
    Thread t2 = new Thread(group, r, "t2");
    t2.start();
  }

  private static void print(Throwable e, String s) {
    System.out.println(s + Thread.currentThread().getName() + "出现异常：" + e.getLocalizedMessage());
  }

}
