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
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * scheduleAtFixedRate时间追赶性
 *
 * @author Sven Augustus
 */
public class T04_Vs_scheduleAtFixedRate {

  // scheduleAtFixedRate(TimerTask task, long delay, long period)
  // 调度一个task，在delay(ms)后开始调度，然后每经过period(ms)再次调度，
  // 貌似和方法：schedule是一样的，其实不然， schedule在计算下一次执行的时间的时候，是通过当前时间（在任务执行前得到） + 时间片，
  // 而scheduleAtFixedRate方法是通过当前需要执行的时间（也就是计算出现在应该执行的时间）+ 时间片，
  // 前者是运行的实际时间，而后者是理论时间点， 例如：schedule时间片是5s，那么理论上会在5、10、15、20这些时间片被调度，
  // 但是如果由于某些CPU征用导致未被调度，假如等到第8s才被第一次调度，
  // 那么schedule方法计算出来的下一次时间应该是第13s而不是第10s，这样有可能下次就越到20s后而被少调度一次或多次，
  // 而scheduleAtFixedRate方法就是每次理论计算出下一次需要调度的时间用以排序，若第8s被调度，
  // 那么计算出应该是第10s，所以它距离当前时间是2s，那么再调度队列排序中，会被优先调度，那么就尽量减少漏掉调度的情况。

  public static void main(String[] args) {
    doRun("schedule", (latch) -> {
      final Timer timer = new Timer();
      timer.schedule(getTask(latch, timer), 0, 3000);
    });
    System.out.println("-----------------------------------------------------");
    doRun("scheduleAtFixedRate", (latch) -> {
      final Timer timer = new Timer();
      timer.scheduleAtFixedRate(getTask(latch, timer), 0, 3000);
    });
  }

  private static void doRun(String title, Consumer<CountDownLatch> consumer) {
    CountDownLatch latch = new CountDownLatch(1);

    final long start = System.currentTimeMillis();
    System.out.println(title + "线程开始时间：" + new Date(start).toLocaleString());

    consumer.accept(latch);

    // 为方便测试，这里等待 Timer 被取消结束
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    long end = System.currentTimeMillis();
    System.out.println(title + "定时器结束时间："
        + new Date(end).toLocaleString() + "耗时：" + (end - start)
        + "ms");
  }

  private static TimerTask getTask(CountDownLatch latch, final Timer timer) {
    return new TimerTask() {

      @Override
      public void run() {
        long thisStart = System.currentTimeMillis();
        System.out.println(
            runCount.get() + " 任务执行begin时间为：" + new Date(thisStart).toLocaleString()
                + "，距离上次begin " + (thisStart - lastBeginTime.get()) + "ms"
                + "，距离上次end "
                + (thisStart - lastEndTime.get()) + "ms");
        try {
          if (runCount.get() == 2) {
            Thread.sleep(4000);
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          if (runCount.get() == 5) {
            timer.cancel();

            // 为方便测试，这里触发 latch 减一
            latch.countDown();
            return;
          }
          long thisEnd = System.currentTimeMillis();
          System.out.println(
              runCount.get() + " 任务执行end  时间为：" + new Date(thisEnd).toLocaleString());

          runCount.set(runCount.get() + 1);
          lastBeginTime.set(thisStart);
          lastEndTime.set(thisEnd);
        }
      }
    };
  }

  private static ThreadLocal<Integer> runCount = new ThreadLocal<Integer>() {

    @Override
    protected Integer initialValue() {
      return 1;
    }

  };
  private static ThreadLocal<Long> lastBeginTime = new ThreadLocal<Long>() {

    @Override
    protected Long initialValue() {
      return System.currentTimeMillis();
    }

  };
  private static ThreadLocal<Long> lastEndTime = new ThreadLocal<Long>() {

    @Override
    protected Long initialValue() {
      return System.currentTimeMillis();
    }

  };

}
