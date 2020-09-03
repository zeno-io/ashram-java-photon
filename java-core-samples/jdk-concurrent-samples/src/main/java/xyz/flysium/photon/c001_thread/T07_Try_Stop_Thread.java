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
 * 如何停止一个线程?
 *
 * @author Sven Augustus
 */
public class T07_Try_Stop_Thread {

  // 停止线程的方式
  // 方式1：interrupt() +return
  // 方法2：interrupt() +抛异常
  // 方法3：stop() ---- 不推荐

  public static void main(String[] args) {
    System.out.println("----------------- interrupt() + return -----------------");
    // 方式1：interrupt() +return
    Thread t1 = new Thread(() -> {
      int sum = 0;
      final int LEN = 10000;
      for (int i = 0; i < LEN; i++) {
        if (Thread.currentThread().isInterrupted()) {
          // return
          System.out.println("T1 end ! ");
          return;
        }
        sum += i;
        if (i % 5 == 0) {
          try {
            TimeUnit.MILLISECONDS.sleep(1);
          } catch (InterruptedException e) {
            // return
            System.out.println("T1 end ! ");
            return;
          }
        }
      }
      System.out.println("T1 finished ! ");
    });
    t1.start();
    t1.interrupt();
    waitForEnd(t1);

    System.out.println("----------------- interrupt() + 抛异常 -----------------");
    // 方法2：interrupt() +抛异常
    Thread t2 = new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        throw new RuntimeException("T2 end !" + e);
      }
      System.out.println("T2 finished ! ");
    });
    t2.start();
    t2.interrupt();
    waitForEnd(t2);

    System.out.println("----------------- stop() -----------------");
    // 方法3：stop() ---- 不推荐
    Thread t31 = new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        System.out.println("T3 end !" + e);
      }
      System.out.println("T3 finished ! ");
    });
    t31.start();
    t31.stop();
    waitForEnd(t31);

    System.out.println("----------------- stop() -----------------");
    // 方法3：stop() ---- 不推荐, 可能导致数据不一致
    final SyncObject lockObject = new SyncObject();
    Thread t32 = new Thread(() -> {
      lockObject.inject("root", "123456");
      System.out.println("T3 finished ! ");
    });
    t32.start();
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    t32.stop();
    System.out.println("lockObject = " + lockObject);
    waitForEnd(t32);
    System.out.println("lockObject = " + lockObject);
  }

  private static class SyncObject {

    private String name;
    private String pwd;

    public synchronized void inject(String name, String pwd) {
      this.name = name;
      try {
        TimeUnit.SECONDS.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      this.pwd = pwd;
    }

    @Override
    public String toString() {
      return "SyncObject{" +
          "name='" + name + '\'' +
          ", pwd='" + pwd + '\'' +
          '}';
    }
  }

  private static void waitForEnd(Thread t1) {
    // wait for end.
    try {
      t1.join();
    } catch (InterruptedException e) {
      // ignore
    }
  }

}
