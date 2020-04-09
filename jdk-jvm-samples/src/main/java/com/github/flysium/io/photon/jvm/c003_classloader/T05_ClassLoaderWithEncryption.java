package com.github.flysium.io.photon.jvm.c003_classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 自定义加密类加载器 ClassLoader
 */
public class T05_ClassLoaderWithEncryption extends MyClassLoader {

  private static final String CLASS_ENCRYPTION = ".classEncryption";

  private static int seed = 0B10110110;

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    try (FileInputStream fis = new FileInputStream(
        new File(PATH, name.replace(".", "/").concat(CLASS_ENCRYPTION))
    );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
      int b = 0;

      while ((b = fis.read()) != 0) {
        baos.write(b ^ seed);
      }

      byte[] bytes = baos.toByteArray();

      return defineClass(name, bytes, 0, bytes.length);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // throws ClassNotFoundException
    return super.findClass(name);
  }

  public void encrypt(String name) throws Exception {
    try (FileInputStream fis = new FileInputStream(
        new File(PATH, name.replace('.', '/').concat(".class")));
        FileOutputStream fos = new FileOutputStream(
            new File(PATH, name.replace(".", "/").concat(CLASS_ENCRYPTION)));) {
      int b = 0;

      while ((b = fis.read()) != -1) {
        fos.write(b ^ seed);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    T05_ClassLoaderWithEncryption classLoader = new T05_ClassLoaderWithEncryption();
    classLoader.encrypt("com.github.flysium.io.photon.jvm.c003_classloader.Hello");

    Class<?> clazz = classLoader.loadClass(
        "com.github.flysium.io.photon.jvm.c003_classloader.Hello"
    );
    Hello h = (Hello) clazz.newInstance();
    h.m();

    System.out.println(classLoader.getClass().getClassLoader());
    System.out.println(classLoader.getParent());
  }


}
