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
 * synchronized 是可重入的特性：当一个线程获取一个对象的锁，再次请求该对象的锁时是可以再次获取该对象的锁的。
 *
 * @author Sven Augustus
 */
public class T04_synchronized_is_reentrant extends Father {

  private synchronized void synchronizedMethod() {
    System.out.println(Thread.currentThread().getName() + " synchronizedMethod");
    synchronizedMethod2();
    // 支持父子类继承的环境, 针对同一个对象的锁,同一个线程可以再次获取该对象的锁的。
    fatherSynchronizedMethod();
  }

  private synchronized void synchronizedMethod2() {
    System.out.println(Thread.currentThread().getName() + " synchronizedMethod2");
  }

  public static void main(String[] args) {
    final T04_synchronized_is_reentrant o = new T04_synchronized_is_reentrant();
    o.synchronizedMethod();
  }

}

class Father {

  protected synchronized void fatherSynchronizedMethod() {
    System.out.println(Thread.currentThread().getName() + " fatherSynchronizedMethod");
  }

}