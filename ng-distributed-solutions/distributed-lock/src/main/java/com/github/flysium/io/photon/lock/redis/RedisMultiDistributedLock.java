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
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * a re-entrant Lock of multi Redis(<a>https://redis.io</a>) instances.
 * <p>
 * see The Redlock algorithm: <a> https://redis.io/topics/distlock </a>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class RedisMultiDistributedLock extends AbstractDistributedLock {

  private final RedissonClient[] clients;
  private final RedissonMultiLock lock;

  public RedisMultiDistributedLock(String lockName, Config... config) {
    if (config == null || config.length == 0) {
      throw new IllegalArgumentException("config can not be null");
    }
    int length = config.length;
    this.clients = new RedissonClient[length];
    RLock[] locks = new RLock[length];
    for (int i = 0; i < length; i++) {
      this.clients[i] = Redisson.create(config[i]);
      locks[i] = this.clients[i].getLock(lockName);
    }
    this.lock = new RedissonRedLock(locks);
  }

  @Override
  public void lock() {
    lock.lock();
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    lock.lockInterruptibly();
  }

  // TODO
  public void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException {
    lock.lockInterruptibly(leaseTime, unit);
  }

  @Override
  public boolean tryLock() {
    return lock.tryLock();
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    return lock.tryLock(time, unit);
  }

  @Override
  public void unlock() {
    lock.unlock();
//    lock.unlockAsync();
  }

  @Override
  public Condition newCondition() {
    return lock.newCondition();
  }

  @Override
  public void close() throws IOException {
    // TODO
    for (RedissonClient client : clients) {
      client.shutdown();
    }
  }

  @Override
  public boolean isReentrantLock() {
    return true;
  }
}
