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

package com.github.flysium.io.photon.lock.redis;

import com.github.flysium.io.photon.lock.AbstractDistributedLock;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * a re-entrant Lock of a single Redis(<a>https://redis.io</a>) instance
 * <p>
 * see {@link org.redisson.RedissonLock}
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class RedisDistributedLock extends AbstractDistributedLock {

  private final RedissonClient client;
  private final RLock rLock;

  public RedisDistributedLock(String lockName, Config config) {
    this.client = Redisson.create(config);
    this.rLock = this.client.getLock(lockName);
  }

  @Override
  public void lock() {
    rLock.lock();
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    rLock.lockInterruptibly();
  }

  // TODO
  public void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException {
    rLock.lockInterruptibly(leaseTime, unit);
  }

  @Override
  public boolean tryLock() {
    return rLock.tryLock();
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    return rLock.tryLock(time, unit);
  }

  @Override
  public void unlock() {
    rLock.unlock();
//    rLock.unlockAsync();
  }

  @Override
  public Condition newCondition() {
    return rLock.newCondition();
  }

  @Override
  public void close() throws IOException {
    // TODO
    client.shutdown();
  }

  @Override
  public boolean isReentrantLock() {
    return true;
  }
}
