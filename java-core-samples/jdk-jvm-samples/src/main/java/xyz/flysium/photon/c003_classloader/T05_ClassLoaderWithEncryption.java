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

package xyz.flysium.photon.c003_classloader;

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
    classLoader.encrypt("xyz.flysium.photon.c003_classloader.Hello");

    Class<?> clazz = classLoader.loadClass(
        "xyz.flysium.photon.c003_classloader.Hello"
    );
    Hello h = (Hello) clazz.newInstance();
    h.m();

    System.out.println(classLoader.getClass().getClassLoader());
    System.out.println(classLoader.getParent());
  }


}
