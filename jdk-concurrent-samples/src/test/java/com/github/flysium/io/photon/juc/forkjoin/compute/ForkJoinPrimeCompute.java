package com.github.flysium.io.photon.juc.forkjoin.compute;


import com.github.flysium.io.photon.juc.MathUtils;
import com.github.flysium.io.photon.juc.forkjoin.IPrimeCompute;
import com.github.flysium.io.photon.juc.forkjoin.task.PrimeRecursiveTask;
import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

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
