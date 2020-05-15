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

package com.github.flysium.io.photon.juc.forkjoin.task;

import com.github.flysium.io.photon.juc.MathUtils;
import java.math.BigInteger;
import java.util.concurrent.Callable;

/**
 * callable task for judge a big number is a prime or not.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class PrimeCallableTask implements Callable<Boolean> {

  private final BigInteger from;
  private final BigInteger to;
  private final BigInteger number;

  public PrimeCallableTask(BigInteger from, BigInteger to, BigInteger number) {
    this.from = from;
    this.to = to;
    this.number = number;
  }

  @Override
  public Boolean call() throws Exception {
    return MathUtils.computeDirectly(from, to, number);
  }

}
