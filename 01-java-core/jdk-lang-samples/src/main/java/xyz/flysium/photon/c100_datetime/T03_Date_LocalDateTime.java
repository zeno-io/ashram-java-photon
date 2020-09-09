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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Date <-> LocalDateTime
 *
 * @author Sven Augustus
 */
public class T03_Date_LocalDateTime {

  public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

  public static void main(String[] args) {
    SimpleDateFormat simpleFormatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

    LocalDateTime time = date2LocalDateTime(new Date());
    System.out.println(dateTimeFormatter.format(time));
    Date date = localDateTime2Date(time);
    System.out.println(simpleFormatter.format(date));
  }

  /**
   * Date转换为LocalDateTime
   */
  public static LocalDateTime date2LocalDateTime(Date date) {
    //An instantaneous point on the time-line.(时间线上的一个瞬时点。)
    Instant instant = date.toInstant();
    //A time-zone ID, such as {@code Europe/Paris}.(时区)
    ZoneId zoneId = ZoneId.systemDefault();
    return instant.atZone(zoneId).toLocalDateTime();
  }

  /**
   * LocalDateTime转换为Date
   */
  public static Date localDateTime2Date(LocalDateTime localDateTime) {
    ZoneId zoneId = ZoneId.systemDefault();
    //Combines this date-time with a time-zone to create a  ZonedDateTime.
    ZonedDateTime zdt = localDateTime.atZone(zoneId);
    return Date.from(zdt.toInstant());
  }

}
