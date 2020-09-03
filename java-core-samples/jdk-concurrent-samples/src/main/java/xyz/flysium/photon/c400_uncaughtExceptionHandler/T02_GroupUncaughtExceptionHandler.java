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

package xyz.flysium.photon.c400_uncaughtExceptionHandler;

/**
 * UncaughtExceptionHandler支持对线程的异常进行捕捉
 *
 * @author Sven Augustus
 */
public class T02_GroupUncaughtExceptionHandler {

  public static void main(String[] args) throws InterruptedException {
    // 1、线程实例有UncaughtExceptionHandler，仅使用该handler
    // 2、如果线程实例没有UncaughtExceptionHandler，但类有静态handler，则通过静态handler处理
    Runnable r = () -> {
      String username = null;
      System.out.println(username.hashCode());
    };
    Thread t1 = new Thread(r, "t1");
    // 对象
    t1.setUncaughtExceptionHandler((t, e) -> print(e, "对象捕捉--线程："));
    // 类
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> print(e, "静态捕捉--线程："));
    t1.start();
    t1.join();

    System.out.println("---------------------------------------------");

    // 1、线程实例有UncaughtExceptionHandler，仅使用该handler
    // 2.1、如果线程实例没有UncaughtExceptionHandler，但类有静态handler，则先通过静态handler处理
    // 2.2、如果线程实例没有UncaughtExceptionHandler，但线程组有UncaughtExceptionHandler，则后通过线程组的handler处理
    ThreadGroup group = new ThreadGroup("group-a") {

      @Override
      public void uncaughtException(Thread t, Throwable e) {
        super.uncaughtException(t, e);
        print(e, "线程组");
      }
    };
    // 类
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> print(e, "静态捕捉--线程："));
    Thread t2 = new Thread(group, r, "t2");
    t2.start();
  }

  private static void print(Throwable e, String s) {
    System.out.println(s + Thread.currentThread().getName() + "出现异常：" + e.getLocalizedMessage());
  }

}
