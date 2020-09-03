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

package xyz.flysium.photon.c050_gc;

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
