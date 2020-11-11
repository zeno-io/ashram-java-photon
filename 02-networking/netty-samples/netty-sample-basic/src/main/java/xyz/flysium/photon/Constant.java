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

package xyz.flysium.photon;

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
