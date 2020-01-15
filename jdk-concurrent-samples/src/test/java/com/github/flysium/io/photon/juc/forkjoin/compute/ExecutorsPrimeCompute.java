package com.github.flysium.io.photon.juc.forkjoin.compute;

import com.github.flysium.io.photon.juc.MathUtils;
import com.github.flysium.io.photon.juc.forkjoin.IPrimeCompute;
import com.github.flysium.io.photon.juc.forkjoin.task.PrimeCallableTask;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
