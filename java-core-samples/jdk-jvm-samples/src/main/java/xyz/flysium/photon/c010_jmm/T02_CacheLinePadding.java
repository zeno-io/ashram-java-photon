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

package xyz.flysium.photon.c010_jmm;

/**
 * 使用缓存行的对齐能够提高效率
 */
public class T02_CacheLinePadding {

  private static class Padding {

    public long p1, p2, p3, p4, p5, p6, p7;
  }

  private static class T extends Padding {

    public volatile long x = 0L;

    public long p8, p9, p10, p11, p12, p13, p14;
  }

  public static T[] arr = new T[2];

  static {
    arr[0] = new T();
    arr[1] = new T();
  }

  public static void main(String[] args) throws Exception {
    Thread t1 = new Thread(() -> {
      for (long i = 0; i < 1000_0000L; i++) {
        arr[0].x = i;
      }
    });

    Thread t2 = new Thread(() -> {
      for (long i = 0; i < 1000_0000L; i++) {
        arr[1].x = i;
      }
    });

    final long start = System.nanoTime();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println((System.nanoTime() - start) / 100_0000);
  }
}
