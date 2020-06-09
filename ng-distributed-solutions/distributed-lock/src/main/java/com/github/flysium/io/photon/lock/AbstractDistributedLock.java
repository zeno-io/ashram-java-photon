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

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract Distributed Lock
 *
 * @author Sven Augustus
 * @version 1.0
 */
public abstract class AbstractDistributedLock implements Lock, Closeable {

  protected final Logger logger = LoggerFactory.getLogger(AbstractDistributedLock.class);

  @Override
  public void lock() {
    try {
      lockInterruptibly();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public Condition newCondition() {
    throw new UnsupportedOperationException();
  }

  protected <T> T futureGet(CompletableFuture<T> future, long timeout, TimeUnit timeUnit)
      throws ExecutionException, InterruptedException, TimeoutException {
    if (timeout < 0 || timeUnit == null) {
      return future.get();
    }
    return future.get(timeout, timeUnit);
  }

  /**
   * Return weather this Lock implementation is re-entrant or not
   *
   * @return return true if it's re-entrant, otherwise return false.
   */
  public abstract boolean isReentrantLock();

}
