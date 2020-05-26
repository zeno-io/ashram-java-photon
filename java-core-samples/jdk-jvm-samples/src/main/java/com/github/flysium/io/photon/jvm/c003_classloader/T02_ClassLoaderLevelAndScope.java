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
 * ClassLoader
 */
public class T02_ClassLoaderLevelAndScope {

  public static void main(String[] args) {
    System.out.println(String.class.getClassLoader());
//    System.out.println(sun.awt.HKSCS.class.getClassLoader());

    System.out.println("-----------------");
    // 父加载器不是“类加载器的父类加载器”，也不是“类加载器的加载器”！！！
    MyClassLoader classLoader = new MyClassLoader();
    System.out.println(classLoader.getParent());
    System.out.println(ClassLoader.getSystemClassLoader());
    System.out.println(T02_ClassLoaderLevelAndScope.class.getClassLoader());
    System.out.println(T02_ClassLoaderLevelAndScope.class.getClassLoader().getClass()
        .getClassLoader());
    System.out.println(T02_ClassLoaderLevelAndScope.class.getClassLoader().getParent());
    System.out.println(T02_ClassLoaderLevelAndScope.class.getClassLoader().getParent().getParent());
    //System.out.println(T02_ClassLoaderLevelAndScope.class.getClassLoader().getParent().getParent().getParent());

    String pathBoot = System.getProperty("sun.boot.class.path");
    System.out.println(pathBoot.replaceAll(";", System.lineSeparator()));

    System.out.println("--------------------");
    String pathExt = System.getProperty("java.ext.dirs");
    System.out.println(pathExt.replaceAll(";", System.lineSeparator()));

    System.out.println("--------------------");
    String pathApp = System.getProperty("java.class.path");
    System.out.println(pathApp.replaceAll(";", System.lineSeparator()));
  }
}
