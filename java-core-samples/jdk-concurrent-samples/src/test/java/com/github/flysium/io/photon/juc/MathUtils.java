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

package com.github.flysium.io.photon.juc;

import java.math.BigInteger;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class MathUtils {

  public static final BigInteger TWO = new BigInteger("2");

  public static boolean computeDirectly(BigInteger from, BigInteger to, BigInteger number) {
    for (BigInteger i = from; i.compareTo(to) <= 0; i = i.add(BigInteger.ONE)) {
      if (number.mod(i).equals(BigInteger.ZERO)) {
        return false;
      }
    }
    return true;
  }

  public static BigInteger sqrt(BigInteger n) {
    BigInteger a = BigInteger.ONE;
    BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8"))
        .toString());
    while (b.compareTo(a) >= 0) {
      BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
      if (mid.multiply(mid).compareTo(n) > 0) {
        b = mid.subtract(BigInteger.ONE);
      } else {
        a = mid.add(BigInteger.ONE);
      }
    }
    return a.subtract(BigInteger.ONE);
  }

}
