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

package xyz.flysium.photon.forkjoin.compute;


import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import xyz.flysium.photon.MathUtils;
import xyz.flysium.photon.forkjoin.IPrimeCompute;
import xyz.flysium.photon.forkjoin.task.PrimeRecursiveTask;

/**
 * fork/join implements for judge a big number is a prime or not.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ForkJoinPrimeCompute implements IPrimeCompute {

  private final int numberOfThread;

  public ForkJoinPrimeCompute(int numberOfThread) {
    this.numberOfThread = numberOfThread <= 1 ? 2 : numberOfThread;
  }

  public boolean compute(BigInteger number) {
    if (number.compareTo(MathUtils.TWO) < 0) {
      return false;
    }
    if (number.compareTo(MathUtils.TWO) == 0) {
      return true;
    }
    BigInteger from = MathUtils.TWO;
    BigInteger to = MathUtils.sqrt(number);
    BigInteger lengthForThread = to.divide(new BigInteger(Integer.toString(numberOfThread)));
    ForkJoinPool pool = new ForkJoinPool();
    ForkJoinTask<Boolean> task = new PrimeRecursiveTask(from, to, number, lengthForThread);
    return pool.invoke(task);
  }

}
