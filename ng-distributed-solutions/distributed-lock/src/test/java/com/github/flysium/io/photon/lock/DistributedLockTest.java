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

package com.github.flysium.io.photon.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class DistributedLockTest {

  protected final Logger logger = LoggerFactory.getLogger(DistributedLockTest.class);

  protected ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 24, 60, TimeUnit.SECONDS,
      new LinkedBlockingQueue<>(),
      Executors.defaultThreadFactory(), new CallerRunsPolicy());

  protected void testIncrement(final AbstractDistributedLock lock, final int threads)
      throws InterruptedException {
    final int[] ints = new int[10];
    ints[0] = 0;
    List<Callable<Void>> callables = new ArrayList<>(threads);
    for (int i = 0; i < threads; i++) {
      callables.add(() -> {
        lockAndHandle(lock, (o) -> {
          ints[0]++;
        });
        return null;
      });
    }
    List<Future<Void>> futures = executor.invokeAll(callables);
    Assert.assertEquals(threads, ints[0]);
  }

  protected void lockAndHandle(final AbstractDistributedLock lock, Consumer<Object> supplier) {
    try {
      lock.lock();
      if (lock.isReentrantLock()) {
        lock.lock();
      }
      supplier.accept(null);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    } finally {
      lock.unlock();
      if (lock.isReentrantLock()) {
        lock.unlock();
      }
    }
  }

}