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
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 *
 * @author Sven Augustus
 */
public class T03_VolatileReferenceDirtyRead {

  private static class Data {

    private int a;
    private int b;

    public Data(int a, int b) {
      this.a = a;
      this.b = b;
    }
  }

  volatile static Data data;

  public static void main(String[] args) {
    new Thread(() -> {
      while (data == null) {
      }
      int x = data.a;
      int y = data.b;
      if (x != y) {
        System.out.printf("a=%s,b=%s\n", x, y);
      }
    }, "ReadThread").start();

    new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        int vi = i;
        data = new Data(vi, vi);
      }
    }, "WriteThread").start();

  }

}

