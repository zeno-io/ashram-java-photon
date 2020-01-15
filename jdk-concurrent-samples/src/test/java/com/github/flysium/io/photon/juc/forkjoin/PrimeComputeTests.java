package com.github.flysium.io.photon.juc.forkjoin;

import com.github.flysium.io.photon.juc.forkjoin.compute.ExecutorsPrimeCompute;
import com.github.flysium.io.photon.juc.forkjoin.compute.ForkJoinPrimeCompute;
import com.github.flysium.io.photon.juc.forkjoin.compute.SimplePrimeCompute;
import java.math.BigInteger;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit Tests for fork/join framework
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class PrimeComputeTests {

  private BigInteger number;

  @Before
  public void setUp() {
    // number = new BigInteger("23333333333");
    number = new BigInteger("23333333333333333");
  }

  @Test
  public void test() {
    long start = System.currentTimeMillis();
    IPrimeCompute compute = new SimplePrimeCompute();
    boolean result = compute.compute(number);
    System.out.println(String.format("test===result==%s", result));
    System.out.println(String.format("test===cost==%d ms", (System.currentTimeMillis() - start)));
  }

  @Test
  public void testWithExecutorService() {
    long start = System.currentTimeMillis();
    IPrimeCompute compute = new ExecutorsPrimeCompute(4);
    boolean result = compute.compute(number);
    System.out.println(String.format("testWithExecutorService===result==%s", result));
    System.out.println(
        String
            .format("testWithExecutorService===cost==%d ms", (System.currentTimeMillis() - start)));
  }

  @Test
  public void testWithForkJoin() {
    long start = System.currentTimeMillis();
    IPrimeCompute compute = new ForkJoinPrimeCompute(4);
    boolean result = compute.compute(number);
    System.out.println(String.format("testWithForkJoin===result==%s", result));
    System.out.println(
        String.format("testWithForkJoin===cost==%d ms", (System.currentTimeMillis() - start)));
  }

}
