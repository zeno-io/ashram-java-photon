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

package xyz.flysium.photon.c103_timer_scheduledthreadpoolexecutor;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Timer简单测试
 *
 * @author Sven Augustus
 */
public class T02_schedule_period {

  // schedule(TimerTask task, Date firstTime, long period) 或者
  // 调度一个task，在指定的时间点time上开始调度，每次调度完后，最少等待 period（ms）后才开始调度。
  // firstTime - 首次执行任务的时间。如果此时间已过去，则安排立即执行该任务。

  // schedule(TimerTask task, long delay, long period)
  // 调度一个task，在delay（ms）后开始调度，每次调度完后，最少等待period（ms）后才开始调度。

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    long future3 = start + TimeUnit.SECONDS.toMillis(3);
    System.out.println("main线程开始时间：" + new java.util.Date(start).toLocaleString());

    final Timer timer = new Timer();
    timer.schedule(new TimerTask() {

      @Override
      public void run() {
        System.out.println("任务执行时间为：" + new Date().toLocaleString());
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, new java.util.Date(future3), 1000);
  }

}
