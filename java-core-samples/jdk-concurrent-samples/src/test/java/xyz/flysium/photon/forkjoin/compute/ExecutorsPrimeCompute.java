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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import xyz.flysium.photon.MathUtils;
import xyz.flysium.photon.forkjoin.IPrimeCompute;
import xyz.flysium.photon.forkjoin.task.PrimeCallableTask;

/**
 * ExecutorService implements for judge a big number is a prime or not.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ExecutorsPrimeCompute implements IPrimeCompute {

  private final int numberOfThread;

  public ExecutorsPrimeCompute(int numberOfThread) {
    this.numberOfThread = numberOfThread <= 1 ? 2 : numberOfThread;
  }

  @Override
  public boolean compute(BigInteger number) {
    if (number.compareTo(MathUtils.TWO) < 0) {
      return false;
    }
    if (number.compareTo(MathUtils.TWO) == 0) {
      return true;
    }
    BigInteger end = MathUtils.sqrt(number);
    BigInteger lengthForThread = end.divide(new BigInteger(Integer.toString(numberOfThread)));

    ExecutorService service = Executors.newCachedThreadPool();
    List<Future<Boolean>> futures = new ArrayList<>();
    for (int i = 0; i < numberOfThread; i++) {
      BigInteger ii = new BigInteger(String.valueOf(i));
      BigInteger from = lengthForThread.multiply(ii);
      BigInteger to = from.add(lengthForThread).subtract(BigInteger.ONE);
      if (from.compareTo(MathUtils.TWO) < 0) {
        from = MathUtils.TWO;
      }
      if (to.compareTo(end) > 0) {
        to = end;
      }
      PrimeCallableTask task = new PrimeCallableTask(from, to, number);
      Future<Boolean> future = service.submit(task);
      futures.add(future);
    }
    service.shutdown();

    boolean result = true;
    try {
      for (Future<Boolean> future : futures) {
        result = result && future.get();
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return result;
  }

}
