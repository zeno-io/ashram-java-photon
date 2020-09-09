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

/**
 * ClassLoader
 */
public class T02_ClassLoaderLevelAndScope {

  public static void main(String[] args) {
    System.out.println(String.class.getClassLoader());
//    System.out.println(sun.awt.HKSCS.class.getClassLoader());

    System.out.println("-----------------");
    // 父加载器不是“类加载器的父类加载器”，也不是“类加载器的加载器”！！！
    MyClassLoader classLoader = new MyClassLoader();
    System.out.println(classLoader.getParent());
    System.out.println(ClassLoader.getSystemClassLoader());
    System.out.println(T02_ClassLoaderLevelAndScope.class.getClassLoader());
    System.out.println(T02_ClassLoaderLevelAndScope.class.getClassLoader().getClass()
        .getClassLoader());
    System.out.println(T02_ClassLoaderLevelAndScope.class.getClassLoader().getParent());
    System.out.println(T02_ClassLoaderLevelAndScope.class.getClassLoader().getParent().getParent());
    //System.out.println(T02_ClassLoaderLevelAndScope.class.getClassLoader().getParent().getParent().getParent());

    System.out.println("--------------------");
    String pathBoot = System.getProperty("sun.boot.class.path");
    System.out.println("sun.boot.class.path: " + pathBoot);

    System.out.println("--------------------");
    String pathExt = System.getProperty("java.ext.dirs");
    System.out.println("java.ext.dirs: " + pathExt);

    System.out.println("--------------------");
    String pathApp = System.getProperty("java.class.path");
    System.out.println("java.class.path: " + pathApp);
  }
}
