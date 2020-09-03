/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.trace;

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
