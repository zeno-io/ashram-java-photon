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
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * benchmark Tests for fork/join framework
 *
 * @author Sven Augustus
 * @version 1.0
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
//@Threads(4)
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

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(PrimeComputeBenchmarkTests.class.getSimpleName())
        .forks(1)
        .build();
    new Runner(opt).run();
  }
/*
  Benchmark                                    Mode  Cnt   Score    Error  Units
  PrimeComputeBenchmarkTests.test                     avgt    5  14.169 ± 0.714  ms/op
  PrimeComputeBenchmarkTests.testWithExecutorService  avgt    5   8.314 ± 0.711  ms/op
  PrimeComputeBenchmarkTests.testWithForkJoin         avgt    5   8.847 ± 1.349  ms/op
*/

}
