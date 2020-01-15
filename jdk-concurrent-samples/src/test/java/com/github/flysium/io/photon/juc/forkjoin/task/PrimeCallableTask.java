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
