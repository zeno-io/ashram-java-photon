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
