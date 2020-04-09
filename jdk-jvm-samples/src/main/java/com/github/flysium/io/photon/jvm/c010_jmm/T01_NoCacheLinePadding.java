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

package com.github.flysium.io.photon.jvm.c010_jmm;

/**
 * 位于同一缓存行的两个不同数据，被两个不同CPU锁定，产生互相影响的伪共享问题
 */
public class T01_NoCacheLinePadding {

  private static class T {

    public volatile long x = 0L;
  }

  public static T[] arr = new T[2];

  static {
    arr[0] = new T();
    arr[1] = new T();
  }

  public static void main(String[] args) throws Exception {
    Thread t1 = new Thread(() -> {
      for (long i = 0; i < 1000_0000L; i++) {
        arr[0].x = i;
      }
    });

    Thread t2 = new Thread(() -> {
      for (long i = 0; i < 1000_0000L; i++) {
        arr[1].x = i;
      }
    });

    final long start = System.nanoTime();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println((System.nanoTime() - start) / 100_0000);
  }
}
