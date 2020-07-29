/*
 * Copyright 2020 SvenAugustus
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
 * 类的热加载，使用 java.lang.instrument 来实现, 代码参考模块 java-jvm-hot
 * <p>
 * 测试VM Option：
 * <pre>
 *   -javaagent:/home/svenaugustus/source/local/photon/java-core-samples/jdk-jvm-samples/lib/jdk_jvm_hot.jar
 * </pre>
 */
public class T09_ClassReloading3 {

  public static void main(String[] args) throws InterruptedException {

    Bean1 c1 = new Bean1();
    while (true) {
      c1.test1();
      Thread.sleep(5000);
    }
  }
}


