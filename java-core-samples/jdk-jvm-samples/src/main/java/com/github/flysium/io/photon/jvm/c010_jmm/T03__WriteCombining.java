/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.jvm.c010_jmm;

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
