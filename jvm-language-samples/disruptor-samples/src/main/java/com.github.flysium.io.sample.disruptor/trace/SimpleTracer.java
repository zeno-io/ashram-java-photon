package com.github.flysium.io.sample.disruptor.trace;

import java.util.concurrent.CountDownLatch;

/**
 * 测试结果跟踪器，计数器不是线程安全的，仅在单线程的 consumer 测试中使用；
 *
 * @author Sven Augustus
 */
public class SimpleTracer implements CounterTracer {

  private long startTicks;
  private long endTicks;
  private long count = 0;
  private boolean end = false;
  private final long expectedCount;
  private final CountDownLatch latch = new CountDownLatch(1);

  public SimpleTracer(long expectedCount) {
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
    count++;
    end = count >= expectedCount;
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
