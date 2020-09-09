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

package xyz.flysium.photon.forkjoin.task;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;
import xyz.flysium.photon.MathUtils;

/**
 * fork/join task for judge a big number is a prime or not.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class PrimeRecursiveTask extends RecursiveTask<Boolean> {

  private final BigInteger from;
  private final BigInteger to;
  private final BigInteger number;
  private final BigInteger lengthForThread;

  public PrimeRecursiveTask(BigInteger from, BigInteger to, BigInteger number,
      BigInteger lengthForThread) {
    this.from = from;
    this.to = to;
    this.number = number;
    this.lengthForThread = lengthForThread;
  }

  @Override
  protected Boolean compute() {
    // If compute is small, just compute directly.
    if (to.subtract(from).compareTo(lengthForThread) <= 0) {
      return MathUtils.computeDirectly(from, to, number);
    }
    // Split task.
    BigInteger mid = to.add(from).divide(MathUtils.TWO);
    PrimeRecursiveTask leftTask = new PrimeRecursiveTask(from, mid, number, lengthForThread);
    PrimeRecursiveTask rightTask = new PrimeRecursiveTask(mid.add(BigInteger.ONE), to, number,
        lengthForThread);
    // Add tasks to fork join pool.
    invokeAll(leftTask, rightTask);
    Boolean leftResult = leftTask.join();
    Boolean rightResult = rightTask.join();
    // Get the result.
    return leftResult && rightResult;
  }
}
