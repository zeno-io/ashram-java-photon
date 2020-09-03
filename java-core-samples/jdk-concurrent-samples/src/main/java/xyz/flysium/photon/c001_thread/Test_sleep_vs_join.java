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

import java.time.LocalDateTime;

/**
 * 采用 sleep, 不释放锁; 采用 t0.join, 可以释放t0对象锁
 *
 * @author Sven Augustus
 */
public class Test_sleep_vs_join {

  // 当释放锁时，t2 进入 testLock 方法 可以在 t0 结束前完成
  // 方式1 Thread.sleep(6000) :
  //  2020-01-06T18:36:22.861 ------- T0 begin ...
  //  2020-01-06T18:36:23.808 T2 准备启动
  //  2020-01-06T18:36:27.862 ------- T0 end !
  //  2020-01-06T18:36:28.809 T2 获得t0对象锁，进入       (可以看到T2需要等待5秒(T1早启动1秒,但睡眠了6秒)过后才能获得锁)
  // 方式2 t0.join(6000) :
  //  2020-01-06T18:35:41.330 ------- T0 begin ...
  //  2020-01-06T18:35:42.256 T2 准备启动
  //  2020-01-06T18:35:42.256 T2 获得t0对象锁，进入       (可以看到几乎启动成功, 就可以获得锁)
  //  2020-01-06T18:35:46.330 ------- T0 end !

  private static class MyThread extends Thread {

    @Override
    public void run() {
      System.out.println(LocalDateTime.now() + " ------- T0 begin ...");
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(LocalDateTime.now() + " ------- T0 end !");
    }

    public synchronized void testLock() {
      System.out.println(LocalDateTime.now() + " T2 获得t0对象锁，进入 ");
    }

  }

  public static void main(String[] args) {
    final MyThread t0 = new MyThread();
    new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (t0) {
          try {
            t0.start();
            // 1.采用 sleep, 不释放锁
            Thread.sleep(6000);

            // 2.采用 t0.join (本质是 t0.wait(xxx)), 可以释放锁
            // T2 线程在启动后可以获得锁
            // t0.join(6000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println("T1 finished !");
      }
    }).start();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(LocalDateTime.now() + " T2 准备启动 ");
    new Thread(new Runnable() {
      @Override
      public void run() {
        t0.testLock();
        System.out.println("T2 finished !");
      }
    }).start();

  }

}
