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

/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 *
 * @author Sven Augustus
 */
public class T03_VolatileReferenceDirtyRead {

  private static class Data {

    private int a;
    private int b;

    public Data(int a, int b) {
      this.a = a;
      this.b = b;
    }
  }

  volatile static Data data;

  public static void main(String[] args) {
    new Thread(() -> {
      while (data == null) {
      }
      int x = data.a;
      int y = data.b;
      if (x != y) {
        System.out.printf("a=%s,b=%s\n", x, y);
      }
    }, "ReadThread").start();

    new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        int vi = i;
        data = new Data(vi, vi);
      }
    }, "WriteThread").start();

  }

}

