package com.github.flysium.io.photon.jvm.c003_classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 自定义类加载器 ClassLoader
 */
public class MyClassLoader extends ClassLoader {

  protected static final String PATH = "/opt/cache/classes";

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    try (FileInputStream fis = new FileInputStream(
        new File(PATH, name.replace(".", "/").concat(".class"))
    );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
      int b = 0;

      while ((b = fis.read()) != 0) {
        baos.write(b);
      }
      byte[] bytes = baos.toByteArray();

      return defineClass(name, bytes, 0, bytes.length);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // throws ClassNotFoundException
    return super.findClass(name);
  }

}

