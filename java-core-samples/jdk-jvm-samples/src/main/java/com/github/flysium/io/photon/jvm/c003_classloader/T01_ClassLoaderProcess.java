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
 * 类加载的过程
 *
 * @author Sven Augustus
 */
public class T01_ClassLoaderProcess {

  // Loading -> Linking （静态成员变量赋默认值）-> Initializing (调用类初始化代码 ，给静态成员变量赋初始值)
  public static void main(String[] args) {
    System.out.println("main~ " + T.count);
    System.out.println("main~ " + T.count);
    System.out.println("main~ " + T.t.getCount());
  }

}

class T {

  // TODO 注意以下两句话的是顺序不同，将导致 main 输出的 count 值不同
  // null
  public static T t = new T();
  // 0
  public static int count = 2;

  private T() {
    System.out.println("construct~ " + count);
    count++;
    System.out.println("construct~ " + count);
  }

  public int getCount() {
    return count;
  }

}
