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

package com.github.flysium.io.photon.jvm.c003_classloader;

/**
 * 严格讲应该叫 lazy Initializing ，因为java虚拟机规范并没有严格规定什么时候必须 loading ,但严格规定了什么时候 Initializing
 */
public class T06_LazyLoading {

  public static void main(String[] args) throws Exception {
    P p;

    // X x = new X();
    // System.out.println(P.i);

    // System.out.println(P.j);

    // Class.forName("com.github.flysium.io.photon.jvm.c003_classloader.T06_LazyLoading$P");
  }

  public static class P {

    final static int i = 8;
    static int j = 9;

    static {
      System.out.println("P");
    }
  }

  public static class X extends P {

    static {
      System.out.println("X");
    }
  }
}
