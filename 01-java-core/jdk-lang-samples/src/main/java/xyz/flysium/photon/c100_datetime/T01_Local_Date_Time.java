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

package xyz.flysium.photon.c100_datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

/**
 * LocalTime、LocalDate、LocalDateTime
 *
 * @author Sven Augustus
 */
public class T01_Local_Date_Time {

  public static void main(String[] args) {
    // 获取当前的日期时间
    LocalDateTime currentTime = LocalDateTime.now();
    System.out.println("当前时间: " + currentTime);

    LocalDate date1 = currentTime.toLocalDate();
    System.out.println("date1: " + date1);

    Month month = currentTime.getMonth();
    int day = currentTime.getDayOfMonth();
    int seconds = currentTime.getSecond();

    System.out.println("月: " + month + ", 日: " + day + ", 秒: " + seconds);

    LocalDateTime date2 = currentTime.withDayOfMonth(10).withYear(2012);
    System.out.println("date2: " + date2);

    // 12 december 2014
    LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
    System.out.println("date3: " + date3);

    // 22 小时 15 分钟
    LocalTime date4 = LocalTime.of(22, 15);
    System.out.println("date4: " + date4);

    // 解析字符串
    LocalTime date5 = LocalTime.parse("20:15:30");
    System.out.println("date5: " + date5);
  }

}
