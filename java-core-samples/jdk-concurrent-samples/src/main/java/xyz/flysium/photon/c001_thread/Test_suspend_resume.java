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
 * suspend() 与resume() 独占同步对象, 导致数据不一致。
 *
 * @author Sven Augustus
 */
public class Test_suspend_resume {

  public static void main(String[] args) {
    final SyncObject lockObject = new SyncObject();
    Thread t1 = new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + " begin...");
      lockObject.testLock();
      System.out.println(Thread.currentThread().getName() + " end!");
    });
    t1.start();
    t1.setName(SUSPEND_THREAD);

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Thread t2 = new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + " begin...");
      lockObject.testLock();
      System.out.println(Thread.currentThread().getName() + " end!");
    });
    t2.start();
    //   t1.resume();
  }

  private static final String SUSPEND_THREAD = "suspendThread";

  private static class SyncObject {

    public synchronized void testLock() {
      System.out.println(Thread.currentThread().getName() + " testLock begin...");
      if (SUSPEND_THREAD.equals(Thread.currentThread().getName())) {
        System.out.println(Thread.currentThread().getName() + " prepare to suspend...");
        Thread.currentThread().suspend();
      }
      System.out.println(Thread.currentThread().getName() + " testLock end");
    }
  }

}
