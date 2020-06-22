package com.github.flysium.io.photon.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Test for parallelStream
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ParallelStreamTest {

  public static void main(String[] args) throws InterruptedException {
    List<Object> a = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      a.add(i);
    }
    // case 1
    a.parallelStream().forEach((o) -> {
      System.out.println(o + "--case1-->" + Thread.currentThread().getName());
    });

    System.out.println("---------------------");
    System.out.println("---------------------");
    System.out.println("---------------------");

    // case 2
    // same for case1 add java options: -Djava.util.concurrent.ForkJoinPool.common.parallelism=3
    ForkJoinPool forkJoinPool2 = new ForkJoinPool(3);
    forkJoinPool2.submit(() -> {
      a.parallelStream().forEach((o) -> {
        System.out.println(o + "--case2-->" + Thread.currentThread().getName());
      });
    });

    TimeUnit.SECONDS.sleep(5);
  }

}
