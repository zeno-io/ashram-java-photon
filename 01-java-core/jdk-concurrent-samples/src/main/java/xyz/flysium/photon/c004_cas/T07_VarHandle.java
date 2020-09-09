/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c004_cas;

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
