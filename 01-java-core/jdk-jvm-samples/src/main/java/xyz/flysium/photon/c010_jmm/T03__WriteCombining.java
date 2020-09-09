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
 * 写操作也可以进行合并
 *
 * @see <a>https://www.cnblogs.com/liushaodong/p/4777308.html</a>
 */
public final class T03__WriteCombining {

  private static final int ITERATIONS = Integer.MAX_VALUE;
  private static final int ITEMS = 1 << 24;
  private static final int MASK = ITEMS - 1;

  private static final byte[] arrayA = new byte[ITEMS];
  private static final byte[] arrayB = new byte[ITEMS];
  private static final byte[] arrayC = new byte[ITEMS];
  private static final byte[] arrayD = new byte[ITEMS];
  private static final byte[] arrayE = new byte[ITEMS];
  private static final byte[] arrayF = new byte[ITEMS];

  public static void main(final String[] args) {
    System.out.println(1 << 24);
    for (int i = 1; i <= 3; i++) {
      System.out.println(i + " SingleLoop duration (ns) = " + runCaseOne());
      System.out.println(i + " SplitLoop  duration (ns) = " + runCaseTwo());
    }
  }

  public static long runCaseOne() {
    long start = System.nanoTime();
    int i = ITERATIONS;

    while (--i != 0) {
      int slot = i & MASK;
      byte b = (byte) i;
      arrayA[slot] = b;
      arrayB[slot] = b;
      arrayC[slot] = b;
      arrayD[slot] = b;
      arrayE[slot] = b;
      arrayF[slot] = b;
    }
    return System.nanoTime() - start;
  }

  public static long runCaseTwo() {
    long start = System.nanoTime();
    int i = ITERATIONS;
    while (--i != 0) {
      int slot = i & MASK;
      byte b = (byte) i;
      arrayA[slot] = b;
      arrayB[slot] = b;
      arrayC[slot] = b;
    }
    i = ITERATIONS;
    while (--i != 0) {
      int slot = i & MASK;
      byte b = (byte) i;
      arrayD[slot] = b;
      arrayE[slot] = b;
      arrayF[slot] = b;
    }
    return System.nanoTime() - start;
  }

}
