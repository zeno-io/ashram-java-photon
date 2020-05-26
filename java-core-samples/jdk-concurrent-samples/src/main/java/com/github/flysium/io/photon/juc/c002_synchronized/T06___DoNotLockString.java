
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

/**
 * 不要以字符串常量作为锁定对象
 *
 * @author Sven Augustus
 */
public class T06___DoNotLockString {

  // 在下面的例子中，m1和m2其实锁定的是同一个对象
  // 这种情况还会发生比较诡异的现象，比如你用到了一个类库，在该类库中代码锁定了字符串“Hello”，
  // 但是你读不到源码，所以你在自己的代码中也锁定了"Hello",这时候就有可能发生非常诡异的死锁阻塞，
  // 因为你的程序和你用到的类库不经意间使用了同一把锁

  String s1 = "Hello";
  String s2 = "Hello";

  void m1() {
    synchronized (s1) {

    }
  }

  void m2() {
    synchronized (s2) {

    }
  }

}
