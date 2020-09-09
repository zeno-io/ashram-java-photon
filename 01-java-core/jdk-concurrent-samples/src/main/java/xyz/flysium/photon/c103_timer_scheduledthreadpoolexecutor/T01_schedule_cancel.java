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
public class T01_schedule_cancel {

  // schedule(TimerTask task, Date time) 在指定的时间点time上调度一次。如果此时间已过去，则安排立即执行该任务。

  // schedule(TimerTask task, long delay) 调度一个task，经过delay(ms)后开始进行调度，仅仅调度一次。 如果 delay 是负数，或者 delay + System.currentTimeMillis() 是负数。

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    long future3 = start + TimeUnit.SECONDS.toMillis(3);
    long past3 = start - TimeUnit.SECONDS.toMillis(3);
    System.out.println("main线程开始时间：" + new java.util.Date(start).toLocaleString());

    final Timer timer = new Timer();
    timer.schedule(new TimerTask() {

      @Override
      public void run() {
        System.out.println("未来3秒任务执行时间为：" + new Date().toLocaleString());
        // 中止定时器timer.cancel()
        // 终止此计时器，丢弃所有当前已安排的任务。这不会干扰当前正在执行的任务（如果存在）。
        // 一旦终止了计时器，那么它的执行线程也会终止，并且无法根据它安排更多的任务。

        //注意，在此计时器调用的计时器任务的 run 方法内调用此方法， 就可以绝对确保正在执行的任务是此计时器所执行的最后一个任务。
        // 可以重复调用此方法；但是第二次和后续调用无效。
        timer.cancel();
      }
    }, new java.util.Date(future3));

    timer.schedule(new TimerTask() {

      @Override
      public void run() {
        System.out.println("过去3秒任务执行时间为：" + new Date().toLocaleString());
      }
    }, new java.util.Date(past3));
  }

}
