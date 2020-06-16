package com.github.flysium.io.sample.disruptor.trace;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试结果跟踪器，针对多线程的 consumer 测试中使用；
 *
 * @author Sven Augustus
 */
public class SafeCountTracer implements CounterTracer {

  private long startTicks;
  private long endTicks;
  private AtomicLong count = new AtomicLong(0L);
  private volatile boolean end = false;
  private final long expectedCount;
  private final CountDownLatch latch = new CountDownLatch(1);

  public SafeCountTracer(long expectedCount) {
    this.expectedCount = expectedCount;
  }

  @Override
  public void start() {
    startTicks = System.currentTimeMillis();
    end = false;
  }

  @Override
  public long getMilliTimeSpan() {
    return endTicks - startTicks;
  }

  @Override
  public boolean count() {
    if (end) {
      return end;
    }
    count.incrementAndGet();
    end = count.longValue() >= expectedCount;
    if (end) {
      endTicks = System.currentTimeMillis();
      latch.countDown();
    }
    return end;
  }

  @Override
  public void waitForReached() throws InterruptedException {
    latch.await();
  }

}
