package com.github.flysium.io.photon.jvm.c010_jmm;

import com.github.flysium.io.photon.jvm.agent.ObjectSizeAgent;

/**
 * <li>
 * -javaagent:/home/svenaugustus/source-repository/flysium-io/photon/jdk-jvm-samples/lib/jdk-jvm-object-size.jar
 * +XX:+PrintCommandLineFlags
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
    System.out.println(ObjectSizeAgent.sizeOf(new Object()));
    System.out.println(ObjectSizeAgent.sizeOf(new int[]{}));
    System.out.println(ObjectSizeAgent.sizeOf(new P()));

    // 启用、禁用指针压缩： 16 16
    // 启用、禁用指针压缩： 16 24
    // 启用、禁用指针压缩： 32 48
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
