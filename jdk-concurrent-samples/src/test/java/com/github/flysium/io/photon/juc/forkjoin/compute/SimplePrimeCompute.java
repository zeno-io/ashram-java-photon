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
