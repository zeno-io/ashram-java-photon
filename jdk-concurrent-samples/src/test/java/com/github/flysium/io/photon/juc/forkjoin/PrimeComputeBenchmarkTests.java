/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.juc.forkjoin;

import com.github.flysium.io.photon.juc.forkjoin.compute.ExecutorsPrimeCompute;
import com.github.flysium.io.photon.juc.forkjoin.compute.ForkJoinPrimeCompute;
import com.github.flysium.io.photon.juc.forkjoin.compute.SimplePrimeCompute;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

/**
 * benchmark Tests for fork/join framework
 *
 * @author Sven Augustus
 * @version 1.0
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Threads(1)
@State(Scope.Benchmark)
@Measurement(iterations = 5)
public class PrimeComputeBenchmarkTests {

  private BigInteger number;

  @Setup
  public void setUp() {
    number = new BigInteger("23333333333");
    //number = new BigInteger("23333333333333333");
  }

  @Benchmark
  public boolean test() {
    IPrimeCompute compute = new SimplePrimeCompute();
    return compute.compute(number);
  }

  @Benchmark
  public boolean testWithExecutorService() {
    IPrimeCompute compute = new ExecutorsPrimeCompute(4);
    return compute.compute(number);
  }

  @Benchmark
  public boolean testWithForkJoin() {
    IPrimeCompute compute = new ForkJoinPrimeCompute(4);
    return compute.compute(number);
  }

//  public static void main(String[] args) throws RunnerException {
//    Options opt = new OptionsBuilder()
//        .include(PrimeComputeBenchmarkTests.class.getSimpleName())
//        .forks(1)
//        .build();
//    new Runner(opt).run();
//  }
/*
  Benchmark                                    Mode  Cnt   Score    Error  Units
  PrimeComputeBenchmarkTests.test                     avgt    5  14.169 ± 0.714  ms/op
  PrimeComputeBenchmarkTests.testWithExecutorService  avgt    5   8.314 ± 0.711  ms/op
  PrimeComputeBenchmarkTests.testWithForkJoin         avgt    5   8.847 ± 1.349  ms/op
*/

}
