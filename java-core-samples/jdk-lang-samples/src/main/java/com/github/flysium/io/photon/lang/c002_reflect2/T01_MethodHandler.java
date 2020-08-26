/*
 * Copyright 2020 SvenAugustus
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

package com.github.flysium.io.photon.lang.c002_reflect2;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * MethodHandle 用法
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T01_MethodHandler {

  public static void main(String[] args) throws Throwable {
    // 调用 构造 方法  ——> findConstructor

    final Demo inst = (Demo) MethodHandles.lookup()
        .findConstructor(Demo.class, MethodType.methodType(void.class)).invoke();

    // 调用 父类 方法  ——> findSpecial
    // access to super and self methods via invokeSpecial:
    // Demo 类 必须有个 lookup() 方法植入
    System.out.println("findSpecial Object.toString->" + Demo.lookup()
        .findSpecial(Object.class, "toString",
            MethodType.methodType(String.class), Demo.class).invoke(inst));
    System.out.println("findSpecial ParentDemo.toString->" + Demo.lookup()
        .findSpecial(ParentDemo.class, "toString",
            MethodType.methodType(String.class), Demo.class).invoke(inst));
    System.out.println("findSpecial Demo.toString->" + Demo.lookup()
        .findSpecial(Demo.class, "toString",
            MethodType.methodType(String.class), Demo.class).invoke(inst));

    System.out.println("findSpecial ParentDemo.publicMethod->" + Demo.lookup()
        .findSpecial(ParentDemo.class, "publicMethod",
            MethodType.methodType(String.class, int.class), Demo.class).invoke(inst, 5));
    System.out.println("findSpecial Demo.publicMethod->" + Demo.lookup()
        .findSpecial(Demo.class, "publicMethod",
            MethodType.methodType(String.class, int.class), Demo.class).invoke(inst, 5));

    // 调用 public 方法  ——> findVirtual

    System.out.println("findVirtual Demo.publicMethod->" + MethodHandles.lookup()
        .findVirtual(Demo.class, "publicMethod",
            MethodType.methodType(String.class, int.class)).invoke(inst, 8));

    System.out.println("findVirtual Demo.publicMethod2->" + MethodHandles.lookup()
        .findVirtual(Demo.class, "publicMethod2",
            MethodType.methodType(String.class, int.class, long.class)).invoke(inst, 2, 6));

    // 调用 public static 方法  ——> findStatic

    System.out.println("findStatic Demo.publicStaticMethod->" + MethodHandles.lookup()
        .findStatic(Demo.class, "publicStaticMethod",
            MethodType.methodType(String.class, int.class)).invoke(18));

    // 调用 private 或 default 方法  ——> Method + unreflect

    System.out.println("Method + unreflect Demo.privateMethod->" + unreflect(Demo.class,
        "privateMethod", int.class).invoke(inst, 5));
    System.out
        .println("Method + unreflect Demo.privateMethod2->" + unreflect(Demo.class,
            "privateMethod2", int.class, long.class).invoke(inst, 2, 3));

    // 调用 private static 方法  ——> Method + unreflect

    System.out.println("Method + unreflect Demo.privateStaticMethod->" + unreflect(Demo.class,
        "privateStaticMethod", int.class).invoke(255));
  }

  public static MethodHandle unreflect(Class<?> refc, String name, Class<?>... parameterTypes)
      throws NoSuchMethodException, IllegalAccessException {
    final Method declaredMethod = refc.getDeclaredMethod(name, parameterTypes);
    declaredMethod.setAccessible(true);
    return MethodHandles.lookup().unreflect(declaredMethod);
  }

  static class ParentDemo {

    public String publicMethod(int m) {
      return m + " in ParentDemo.publicMethod";
    }

    @Override
    public String toString() {
      return "[parent Demo]";
    }

  }

  static class Demo extends ParentDemo {

    Demo() {
      System.out.println("new ~");
    }

    @Override
    public String publicMethod(int m) {
      return m + " in publicMethod";
    }

    public String publicMethod2(int x, long y) {
      return x + "," + y + " in publicMethod2";
    }

    private String privateMethod(int m) {
      return m + " in privateMethod";
    }

    String privateMethod2(int x, long y) {
      return x + "," + y + " in privateMethod2";
    }

    public static String publicStaticMethod(int m) {
      return m + " in publicStaticMethod";
    }

    private static String privateStaticMethod(int m) {
      return m + " in privateStaticMethod";
    }

    @Override
    public String toString() {
      return "[zeno Demo]";
    }

    public static Lookup lookup() {
      return MethodHandles.lookup();
    }
  }

}
