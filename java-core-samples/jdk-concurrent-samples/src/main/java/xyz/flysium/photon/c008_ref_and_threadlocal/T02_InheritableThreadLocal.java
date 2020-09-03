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

package xyz.flysium.photon.c008_ref_and_threadlocal;

import java.util.Date;

/**
 * InheritableThreadLocal 子线程可以读取父线程的值，但反之不行
 *
 * @author Sven Augustus
 */
public class T02_InheritableThreadLocal {

  static InheritableThreadLocal<Date> tl = new InheritableThreadLocal<Date>() {

    @Override
    protected Date initialValue() {
      return new Date();
    }

  };

  private static void read() {
    try {
      for (int i = 0; i < 3; i++) {
        System.out.println(Thread.currentThread().getName() + " " + tl.get().getTime());
        Thread.sleep(100);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws InterruptedException {

    Thread a = new Thread(() -> {
      tl.set(new Date());
      read();
    }, "A");
    a.start();

    Thread.sleep(3000);

    read();

    Thread.sleep(3000);

    Thread b = new Thread(() -> {
      read();
    }, "B");
    b.start();

    a.join();
    b.join();
    tl.remove();
  }


}
