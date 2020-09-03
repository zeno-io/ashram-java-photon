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
