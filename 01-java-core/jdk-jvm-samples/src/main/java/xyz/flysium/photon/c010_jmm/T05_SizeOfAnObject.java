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

package xyz.flysium.photon.c010_jmm;

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
