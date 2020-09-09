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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 类的重新加载
 */
public class T09_ClassReloading2 {

  private static class MyReloadingClassLoader extends MyClassLoader {

    // 这个并不能真正做到 hot reload, 只是表明 如何绕开 双亲委派，也可以参考 Tomcat 的 JasperLoader，每次加载也是 new 一个 新的 JasperLoader 重新加载class
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
        "xyz.flysium.photon.c003_classloader.Hello"
    );
    System.out.println(clazz.hashCode());

    classLoader = new MyReloadingClassLoader();
    Class<?> clazzNew = classLoader.loadClass(
        "xyz.flysium.photon.c003_classloader.Hello"
    );
    System.out.println(clazzNew.hashCode());

    System.out.println(clazz == clazzNew);
  }
}
