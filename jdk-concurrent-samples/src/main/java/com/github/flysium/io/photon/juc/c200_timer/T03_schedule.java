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

package com.github.flysium.io.photon.juc.c200_timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Timer简单测试 -- 顺序地执行，注意制定计划时间可能与实际运行时间不一致。
 *
 * @author Sven Augustus
 */
public class T03_schedule {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    final long future3 = start + TimeUnit.SECONDS.toMillis(3);
    final long future6 = start + TimeUnit.SECONDS.toMillis(6);
    System.out.println("main线程开始时间：" + new java.util.Date(start).toLocaleString());

    final Timer timer = new Timer();
    timer.schedule(new TimerTask() {

      @Override
      public void run() {
        System.out.println("未来3秒任务执行时间为：" + new Date().toLocaleString());
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, new java.util.Date(future3));

    timer.schedule(new TimerTask() {

      @Override
      public void run() {
        System.out.println("未来6秒任务计划执行时间为：" + new java.util.Date(future6).toLocaleString());
        System.out.println("未来6秒任务实际执行时间为：" + new Date().toLocaleString());
      }
    }, new java.util.Date(future6));
  }

}
