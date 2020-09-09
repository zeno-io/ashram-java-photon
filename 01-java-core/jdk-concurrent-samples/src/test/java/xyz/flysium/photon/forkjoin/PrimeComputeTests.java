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

package xyz.flysium.photon.forkjoin;

import java.math.BigInteger;
import org.junit.Before;
import org.junit.Test;
import xyz.flysium.photon.forkjoin.compute.ExecutorsPrimeCompute;
import xyz.flysium.photon.forkjoin.compute.ForkJoinPrimeCompute;
import xyz.flysium.photon.forkjoin.compute.SimplePrimeCompute;

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
