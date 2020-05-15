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

/**
 * <li>
 * -XX:+PrintGCDetails -XX:+PrintCommandLineFlags
 * </li>
 *
 * @author Sven Augustus
 */
public class T01_HelloGC {

  public static void main(String[] args) {

    for (int i = 0; i < 10000; i++) {
      byte[] b = new byte[1024 * 1024];
    }
  }
}
