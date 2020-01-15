package com.github.flysium.io.photon.juc.forkjoin;

import java.math.BigInteger;

/**
 *  judge a big number is a prime or not.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public interface IPrimeCompute {

  /**
   * judge a big number is a prime or not.
   *
   * @param number number
   * @return is a prime or not
   */
  boolean compute(BigInteger number);

}
