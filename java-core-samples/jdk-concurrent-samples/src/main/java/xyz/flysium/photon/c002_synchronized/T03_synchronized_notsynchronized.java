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

package xyz.flysium.photon.c002_synchronized;

/**
 * synchronized方法  与 非synchronized方法 可以 同时执行,交叉执行 。
 *
 * @author Sven Augustus
 */
public class T03_synchronized_notsynchronized {

  private static void normalMethod() {
    System.out.println(Thread.currentThread().getName() + " normalMethod");
  }

  private static synchronized void synchronizedMethod() {
    System.out.println(Thread.currentThread().getName() + " synchronizedMethod");
  }

  private static synchronized void synchronizedMethod2() {
    System.out.println(Thread.currentThread().getName() + " synchronizedMethod2");
    normalMethod();
  }

  public static void main(String[] args) {
    new Thread(T03_synchronized_notsynchronized::synchronizedMethod, "T1").start();
    new Thread(T03_synchronized_notsynchronized::synchronizedMethod2, "T2").start();
    new Thread(T03_synchronized_notsynchronized::normalMethod, "T3").start();
  }

}
