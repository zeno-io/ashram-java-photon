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

package xyz.flysium.photon.c003_volatile;

/**
 * synchronized 既可以保证可见性，还可以保证原子性。 volatile 只能保证可见性，不保证原子性。
 *
 * @author Sven Augustus
 */
public class T05_VolatileVsSync {

  // count 不是 1000000
  volatile static int count = 0;

  private static void increment() {
    for (int i = 0; i < 10000; i++) {
      count++;
    }
  }

  private static synchronized void synchronizedIncrement() {
    increment();
  }

  public static void main(String[] args) {
    Thread[] threads = new Thread[100];
    for (int i = 0; i < 100; i++) {
      Thread t = new Thread(() -> {
        increment();
        // synchronizedIncrement();
      });
      threads[i] = t;
      t.start();
    }

    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println("count=" + count);
  }

}
