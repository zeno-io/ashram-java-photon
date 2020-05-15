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

package com.github.flysium.io.photon.lang.c100_datetime;

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
