package com.github.flysium.io.photon.jvm.c003_classloader;

/**
 * 利用类加载器加载资源
 */
public class T03_LoadClass {

  public static void main(String[] args) throws ClassNotFoundException {
    Class<?> clazz = T03_LoadClass.class.getClassLoader().loadClass(
        "com.github.flysium.io.photon.jvm.c003_classloader.T03_LoadClass"
    );
    System.out.println(clazz.getName());
  }

}
