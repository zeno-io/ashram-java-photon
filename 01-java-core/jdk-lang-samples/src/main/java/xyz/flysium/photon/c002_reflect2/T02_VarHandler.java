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

package xyz.flysium.photon.c002_reflect2;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;

/**
 * VarHandler 用法
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T02_VarHandler {

  public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
    Demo inst = new Demo();

    // findVarHandle
    VarHandle varHandle = MethodHandles.privateLookupIn(Demo.class, MethodHandles.lookup())
        .findVarHandle(Demo.class, "privateVar", int.class);
    System.out.println("get privateVar -> " + varHandle.get(inst));
    varHandle.set(inst, 33);
    System.out.println("after set privateVar -> " + varHandle.get(inst));

    varHandle = MethodHandles.privateLookupIn(Demo.class, MethodHandles.lookup())
        .findVarHandle(Demo.class, "publicVar", int.class);
    System.out.println("get publicVar -> " + varHandle.get(inst));
    varHandle.set(inst, 10);
    System.out.println("after set publicVar -> " + varHandle.get(inst));

    varHandle = MethodHandles.privateLookupIn(Demo.class, MethodHandles.lookup())
        .findVarHandle(Demo.class, "protectedVar", int.class);
    System.out.println("get protectedVar -> " + varHandle.get(inst));
    varHandle.set(inst, 20);
    System.out.println("after set protectedVar -> " + varHandle.get(inst));

    // findStaticVarHandle
    varHandle = MethodHandles.privateLookupIn(Demo.class, MethodHandles.lookup())
        .findStaticVarHandle(Demo.class, "staticVar", long.class);
    System.out.println("get staticVar -> " + varHandle.get());
    varHandle.set(40);
    System.out.println("after set staticVar -> " + varHandle.get());

    // 数组
    varHandle = MethodHandles.privateLookupIn(Demo.class, MethodHandles.lookup())
        .findVarHandle(Demo.class, "arrayData", int[].class);
    System.out.println("get arrayData -> " + Arrays.toString((int[]) varHandle.get(inst)));
    varHandle.set(inst, new int[]{300, 400, 500});
    System.out.println("after set arrayData -> " + Arrays.toString((int[]) varHandle.get(inst)));

    // arrayElementVarHandle
    final int[] o = (int[]) varHandle.get(inst);
    final VarHandle handle = MethodHandles.arrayElementVarHandle(int[].class);
    handle.compareAndSet(o, 0, 300, 700);
    handle.compareAndSet(o, 1, 400, 800);
    handle.compareAndSet(o, 2, 500, 900);
    System.out.println("after set arrayData -> " + Arrays.toString((int[]) o));
    System.out.println("after set arrayData 0 -> " + handle.get(o, 0));
    System.out.println("after set arrayData 1 -> " + handle.get(o, 1));
    System.out.println("after set arrayData 2 -> " + handle.get(o, 2));

  }

  static class Demo {

    public int publicVar = 1;
    protected int protectedVar = 2;
    private int privateVar = 3;
    private static long staticVar = 4L;
    private int[] arrayData = new int[]{100, 200, 300};
  }

}
