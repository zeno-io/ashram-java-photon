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

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * ZoneId、ZonedDateTime
 *
 * @author Sven Augustus
 */
public class T02_ZonedDateTime {

  public static void main(String[] args) {
    // 获取当前时间日期
    ZonedDateTime date1 = ZonedDateTime.parse("2015-12-03T10:15:30+05:30[Asia/Shanghai]");
    System.out.println("date1: " + date1);

    ZoneId id = ZoneId.of("Europe/Paris");
    System.out.println("ZoneId: " + id);

    ZoneId currentZone = ZoneId.systemDefault();
    System.out.println("当期时区: " + currentZone);
  }

}
