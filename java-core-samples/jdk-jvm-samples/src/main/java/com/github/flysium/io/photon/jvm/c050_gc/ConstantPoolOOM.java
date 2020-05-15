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

package com.github.flysium.io.photon.jvm.c050_gc;

import java.util.LinkedList;
import java.util.List;

/**
 * 常量池内存溢出
 *
 * @author Sven Augustusd
 */
public class ConstantPoolOOM {

  static long i = 0;

  public static void main(String[] args) {
    List<String> strings = new LinkedList<>();
    for (; ; ) {
      strings.add("" + i++);
    }
  }
}
