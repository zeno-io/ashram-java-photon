/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.juc.c103_timer_scheduledthreadpoolexecutor;

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
