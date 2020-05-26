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

package com.github.flysium.io.photon.jvm;

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
