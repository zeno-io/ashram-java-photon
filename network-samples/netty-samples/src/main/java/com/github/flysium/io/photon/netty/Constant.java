/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.netty;

import java.util.Random;

/**
 * Constant
 *
 * @author Sven Augustus
 * @version 1.0
 */
public final class Constant {

  private Constant() {
  }

  public static final String HOST = "127.0.0.1";

  public static final int PORT = 8090;

  public static final String DELIMITER = "\n";

  public static final String BIG_STRING_1024 = randomString(1024);

  public static final String BIG_STRING_2048 = randomString(2048);

  private static final String DICT_STRING = "abcdefghjkmnopqrstuvwxyz023456789";

  public static String randomString(int length) {
    return randomString(length, length);
  }

  public static String randomString(int min, int max) {
    Random random = new Random();
    int length = min + ((max > min) ? random.nextInt(max - min) : 0);
    StringBuilder nameString = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      nameString.append(DICT_STRING.charAt(random.nextInt(DICT_STRING.length())));
    }
    return nameString.toString();
  }

}
