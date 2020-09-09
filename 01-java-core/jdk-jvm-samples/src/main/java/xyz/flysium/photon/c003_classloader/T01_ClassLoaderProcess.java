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
 * 类加载的过程
 *
 * @author Sven Augustus
 */
public class T01_ClassLoaderProcess {

  // Loading -> Linking （静态成员变量赋默认值）-> Initializing (调用类初始化代码 ，给静态成员变量赋初始值)
  public static void main(String[] args) {
    System.out.println("main~ " + T.count);
    System.out.println("main~ " + T.count);
    System.out.println("main~ " + T.t.getCount());
  }

}

class T {

  // TODO 注意以下两句话的是顺序不同，将导致 main 输出的 count 值不同
  // null
  public static T t = new T();
  // 0
  public static int count = 2;

  private T() {
    System.out.println("construct~ " + count);
    count++;
    System.out.println("construct~ " + count);
  }

  public int getCount() {
    return count;
  }

}
