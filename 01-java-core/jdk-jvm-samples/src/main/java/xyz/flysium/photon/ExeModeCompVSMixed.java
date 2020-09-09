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

package xyz.flysium.photon;

/**
 * -Xcomp的作用，开启编译模式
 */
public class ExeModeCompVSMixed {

  public static void main(String[] args) {
    long t = System.currentTimeMillis();
    for (int i = 0; i < 1000; i++) {
      getMemoryInfo();
    }
    System.out.println(System.currentTimeMillis() - t);
  }

  static String getMemoryInfo() {
    double pi = 3.14;

    for (long i = 0; i < 1_0000L; i++) {
      pi = 3.14 / 2.58;
      pi = 3.14;
      long t = Runtime.getRuntime().totalMemory();
      new ExeModeCompVSMixed();
    }

    return "";
  }

}
