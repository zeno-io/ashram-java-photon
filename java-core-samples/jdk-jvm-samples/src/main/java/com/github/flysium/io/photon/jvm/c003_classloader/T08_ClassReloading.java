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
 * 类的重新加载
 */
public class T08_ClassReloading {

  public static void main(String[] args) throws Exception {
    MyClassLoader classLoader = new MyClassLoader();
    Class<?> clazz = classLoader.loadClass(
        "com.github.flysium.io.photon.jvm.c003_classloader.Hello"
    );

    classLoader = null;
    System.out.println(clazz.hashCode());

    classLoader = null;

    classLoader = new MyClassLoader();
    Class<?> clazz1 = classLoader.loadClass(
        "com.github.flysium.io.photon.jvm.c003_classloader.Hello"
    );
    System.out.println(clazz1.hashCode());

    System.out.println(clazz == clazz1);
  }
}
