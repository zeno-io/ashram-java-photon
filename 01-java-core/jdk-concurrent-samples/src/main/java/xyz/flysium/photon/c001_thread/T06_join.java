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

package xyz.flysium.photon.c001_thread;

import java.util.concurrent.TimeUnit;

/**
 * t.join() 等待该线程t 销毁终止。
 *
 * @author Sven Augustus
 */
public class T06_join {

  // 使用 join 将 t1 、t2、t3 按顺序执行了

  public static void main(String[] args) {
    Thread t1 = new Thread(() -> {
      try {
        TimeUnit.MILLISECONDS.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("T1 finished !");
    });

    Thread t2 = new Thread(() -> {
      // t2 等待 t1 执行完毕
      try {
        t1.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("T2 finished !");
    });

    Thread t3 = new Thread(() -> {
      // t3 等待 t2 执行完毕
      try {
        t2.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("T3 finished !");
    });

    t1.start();
    t2.start();
    t3.start();
  }

}
