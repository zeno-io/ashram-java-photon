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

package com.github.flysium.io.photon.juc.c002_synchronized;

/**
 * synchronized方法  与 非synchronized方法 可以 同时执行,交叉执行 。
 *
 * @author Sven Augustus
 */
public class T03_synchronized_notsynchronized {

  private static void normalMethod() {
    System.out.println(Thread.currentThread().getName() + " normalMethod");
  }

  private static synchronized void synchronizedMethod() {
    System.out.println(Thread.currentThread().getName() + " synchronizedMethod");
  }

  private static synchronized void synchronizedMethod2() {
    System.out.println(Thread.currentThread().getName() + " synchronizedMethod2");
    normalMethod();
  }

  public static void main(String[] args) {
    new Thread(T03_synchronized_notsynchronized::synchronizedMethod, "T1").start();
    new Thread(T03_synchronized_notsynchronized::synchronizedMethod2, "T2").start();
    new Thread(T03_synchronized_notsynchronized::normalMethod, "T3").start();
  }

}
