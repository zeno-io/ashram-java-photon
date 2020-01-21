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

package com.github.flysium.io.photon.juc.c004_cas;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * VarHandle 变量句柄
 *
 * @author Sven Augustus
 */
public class T07_VarHandle {

  // VarHandle 可以进行普通属性的get、set, volatile属性的get、set,甚至 compareAndSet 、getAndAdd 等原子操作，比 反射 更高效 （反射每次使用前需要做检查）

  // 变量句柄（VarHandle）是对于一个变量的强类型引用，或者是一组参数化定义的变量族，包括了静态字段、非静态字段、数组元素等，
  // VarHandle支持不同访问模型下对于变量的访问，包括简单的read/write访问，volatile read/write访问，以及CAS访问。
  // VarHandle相比于传统的对于变量的并发操作具有巨大的优势，在JDK9引入了VarHandle之后，JUC包中对于变量的访问基本上都使用VarHandle，比如AQS中的CLH队列中使用到的变量等。

  int x = 8;

  public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
    VarHandle handle = MethodHandles.lookup()
        .findVarHandle(T07_VarHandle.class, "x", int.class);

    T07_VarHandle t = new T07_VarHandle();

    // read / write
    System.out.println((int) handle.get(t));
    handle.set(t, 9);
    System.out.println(t.x);

    // cas
    handle.compareAndSet(t, 9, 10);
    System.out.println(t.x);

    handle.getAndAdd(t, 10);
    System.out.println(t.x);
  }

}
