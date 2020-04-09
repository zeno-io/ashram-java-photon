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

package com.github.flysium.io.photon.jvm.c050_gc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 从数据库中读取信用数据，套用模型，并把结果进行记录和传输
 *
 * <li>
 * -Xms200M -Xmx200M -XX:+PrintGC
 * </li>
 *
 * @author Sven Augustus
 */
public class T02_FullGC_Problem01 {

  private static class CardInfo {

    BigDecimal price = new BigDecimal("0.0");
    String name = "张三";
    int age = 5;
    Date birthdate = new Date();

    public void m() {
    }
  }

  private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(50,
      new ThreadPoolExecutor.DiscardOldestPolicy());

  public static void main(String[] args) throws Exception {
    executor.setMaximumPoolSize(50);

    for (; ; ) {
      modelFit();
      Thread.sleep(100);
    }
  }

  private static void modelFit() {
    List<CardInfo> taskList = getAllCardInfo();
    taskList.forEach(info -> {
      // do something
      // do sth with info
      executor.scheduleWithFixedDelay(info::m, 2, 3, TimeUnit.SECONDS);
    });
  }

  private static List<CardInfo> getAllCardInfo() {
    List<CardInfo> taskList = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      CardInfo ci = new CardInfo();
      taskList.add(ci);
    }

    return taskList;
  }
}
