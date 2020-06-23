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

package com.github.flysium.io.photon.jvm.c010_jmm;

import com.carrotsearch.sizeof.RamUsageEstimator;
import com.github.flysium.io.photon.jvm.agent.ObjectSizeAgent;
import org.github.jamm.MemoryMeter;

/**
 * </li>
 * 默认是开启 Class Pointers 和 OOps 压缩的
 * <li>
 * 不启用压缩： -XX:-UseCompressedClassPointers -XX:-UseCompressedOops
 * </li>
 *
 * @author Sven Augustus
 */
public class T05_SizeOfAnObject {

  public static void main(String[] args) {
    int case0 = 2;
    switch (case0) {
      case 0:
        // -javaagent:/home/svenaugustus/source-repository/flysium-io/photon/jdk-jvm-samples/lib/jdk-jvm-object-size.jar -XX:+PrintCommandLineFlags
        System.out.println(measureByJavaagent0(new Object()));
        System.out.println(measureByJavaagent0(new int[]{}));
        System.out.println(measureByJavaagent0(new P()));
        break;
      case 1:
        // -javaagent:/var/lib/maven/defaults/com/github/jbellis/jamm/0.3.3/jamm-0.3.3.jar
        System.out.println(measureByJavaagent1(new Object()));
        System.out.println(measureByJavaagent1(new int[]{}));
        System.out.println(measureByJavaagent1(new P()));
        break;
      case 2:
      default:
        System.out.println(RamUsageEstimator.humanSizeOf(new Object()));
        System.out.println(RamUsageEstimator.humanSizeOf(new int[]{}));
        System.out.println(RamUsageEstimator.humanSizeOf(new P()));
        break;
    }
    // 启用、禁用指针压缩： 16 16
    // 启用、禁用指针压缩： 16 24
    // 启用、禁用指针压缩： 32 48
  }

  public static long measureByJavaagent0(Object o) {
    return ObjectSizeAgent.sizeOf(o);
  }

  public static long measureByJavaagent1(Object o) {
    MemoryMeter memoryMeter = new MemoryMeter();
    return memoryMeter.measure(o);
  }


  // 一个Object占多少个字节
  // -XX:+UseCompressedClassPointers -XX:+UseCompressedOops
  // Oops = ordinary object pointers
  private static class P {

    //8 _markword
    //4 _class pointer
    int id;         //4
    String name;    //4
    int age;        //4

    byte b1;        //1
    byte b2;        //1

    Object o;       //4
    byte b3;        //1

  }
}
