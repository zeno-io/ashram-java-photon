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
 * synchronized 是可重入的特性：当一个线程获取一个对象的锁，再次请求该对象的锁时是可以再次获取该对象的锁的。
 *
 * @author Sven Augustus
 */
public class T04_synchronized_is_reentrant extends Father {

  private synchronized void synchronizedMethod() {
    System.out.println(Thread.currentThread().getName() + " synchronizedMethod");
    synchronizedMethod2();
    // 支持父子类继承的环境, 针对同一个对象的锁,同一个线程可以再次获取该对象的锁的。
    fatherSynchronizedMethod();
  }

  private synchronized void synchronizedMethod2() {
    System.out.println(Thread.currentThread().getName() + " synchronizedMethod2");
  }

  public static void main(String[] args) {
    final T04_synchronized_is_reentrant o = new T04_synchronized_is_reentrant();
    o.synchronizedMethod();
  }

}

class Father {

  protected synchronized void fatherSynchronizedMethod() {
    System.out.println(Thread.currentThread().getName() + " fatherSynchronizedMethod");
  }
  
}