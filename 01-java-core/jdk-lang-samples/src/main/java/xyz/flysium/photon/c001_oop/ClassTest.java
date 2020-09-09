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

package xyz.flysium.photon.c001_oop;

/**
 * 创建对象时构造器的调用顺序
 *
 * @author Sven Augustus
 * @version 2016年10月30日
 */
public class ClassTest {

  /**
   * 创建对象时构造器的调用顺序是：
   * <p>
   * 先初始化静态成员，然后调用父类构造器，再初始化非静态成员，最后调用自身构造器。
   */
  @SuppressWarnings("unused")
  public static void main(String[] args) {
    // A b2 = new B();
    B b = new B();
    A b2 = new B();
    A c = new C();
    // ab = new B();
  }

}

class A {

  /**
   * f1 -- 在该类或子类第一次new的时候第一个执行
   */
  static {
    System.out.print("A-static ");
  }

  /**
   * 在该类new的时候执行，或子类默认new的时候先执行
   */
  public A() {
    System.out.print("A-construction ");
  }
}

class B extends A {

  static {
    System.out.print("B-static ");
  }

  public B() {
    System.out.print("B-construction ");
  }
}

class C extends A {

  static {
    System.out.print("C-static ");
  }

  public C() {
    System.out.print("C-construction ");
  }
}