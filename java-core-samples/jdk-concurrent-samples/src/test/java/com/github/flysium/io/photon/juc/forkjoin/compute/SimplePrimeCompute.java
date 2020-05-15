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

package com.github.flysium.io.photon.juc.forkjoin.compute;

import com.github.flysium.io.photon.juc.MathUtils;
import com.github.flysium.io.photon.juc.forkjoin.IPrimeCompute;
import java.math.BigInteger;

/**
 * simple implements for judge a big number is a prime or not.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class SimplePrimeCompute implements IPrimeCompute {

  @Override
  public boolean compute(BigInteger number) {
    if (number.compareTo(MathUtils.TWO) < 0) {
      return false;
    }
    if (number.compareTo(MathUtils.TWO) == 0) {
      return true;
    }
    BigInteger from = MathUtils.TWO;
    BigInteger to = MathUtils.sqrt(number);
    return MathUtils.computeDirectly(from, to, number);
  }

}
