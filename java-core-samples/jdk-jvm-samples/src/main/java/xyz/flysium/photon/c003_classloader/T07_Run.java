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
 * 混合执行 编译执行 解释执行
 */
public class T07_Run {

//    -Xmixed 默认为混合模式，开始解释执行，启动速度较快，对热点代码实行检测和编译
//        检测热点代码： -XX:CompileThreshold=10000
//    -Xint 使用解释模式，启动很快，执行稍慢
//    -Xcomp 使用纯编译模式，执行很快，启动很慢

  public static void main(String[] args) {
    for (int i = 0; i < 10_00; i++) {
      m();
    }

    long start = System.currentTimeMillis();
    for (int i = 0; i < 10_0000; i++) {
      m();
    }
    long end = System.currentTimeMillis();
    System.out.println(end - start);
  }

  public static void m() {
    for (long i = 0; i < 10_0000L; i++) {
      long j = i % 3;
    }
  }
}
