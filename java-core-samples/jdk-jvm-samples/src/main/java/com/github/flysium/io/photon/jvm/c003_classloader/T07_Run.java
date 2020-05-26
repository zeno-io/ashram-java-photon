/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.jvm.c003_classloader;

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
