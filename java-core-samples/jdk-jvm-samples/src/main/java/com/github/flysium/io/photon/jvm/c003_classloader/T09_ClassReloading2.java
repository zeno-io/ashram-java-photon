package com.github.flysium.io.photon.jvm.c003_classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 类的重新加载
 */
public class T09_ClassReloading2 {

  private static class MyReloadingClassLoader extends MyClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
      File f = new File(PATH, name.replace(".", "/").concat(".class"));
      if (!f.exists()) {
        return super.loadClass(name);
      }

      try (FileInputStream fis = new FileInputStream(f);) {
        byte[] b = new byte[fis.available()];
        fis.read(b);

        return defineClass(name, b, 0, b.length);
      } catch (IOException e) {
        e.printStackTrace();
      }

      return super.loadClass(name);
    }
  }

  public static void main(String[] args) throws Exception {
    MyReloadingClassLoader classLoader = new MyReloadingClassLoader();
    Class<?> clazz = classLoader.loadClass(
        "com.github.flysium.io.photon.jvm.c003_classloader.Hello"
    );
    System.out.println(clazz.hashCode());

    classLoader = new MyReloadingClassLoader();
    Class<?> clazzNew = classLoader.loadClass(
        "com.github.flysium.io.photon.jvm.c003_classloader.Hello"
    );
    System.out.println(clazzNew.hashCode());

    System.out.println(clazz == clazzNew);
  }
}
