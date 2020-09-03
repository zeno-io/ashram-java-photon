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

package xyz.flysium.photon.c003_classloader;

/**
 * 严格讲应该叫 lazy Initializing ，因为java虚拟机规范并没有严格规定什么时候必须 loading ,但严格规定了什么时候 Initializing
 */
public class T06_LazyLoading {

  public static void main(String[] args) throws Exception {
    P p;

    //X x = new X();

    //System.out.println(P.i);

    //  System.out.println(P.j);

    // Class.forName("xyz.flysium.photon.c003_classloader.T06_LazyLoading$P");
  }

  public static class P {

    final static int i = 8;
    static int j = 9;

    static {
      System.out.println("P");
    }
  }

  public static class X extends P {

    static {
      System.out.println("X");
    }
  }
}
