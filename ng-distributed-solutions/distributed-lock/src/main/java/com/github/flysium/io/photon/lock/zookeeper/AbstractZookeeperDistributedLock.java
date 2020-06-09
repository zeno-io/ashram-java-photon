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

package com.github.flysium.io.photon.lock.zookeeper;

import com.github.flysium.io.photon.lock.AbstractDistributedLock;
import com.github.flysium.io.photon.lock.LockInterruptedException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.utils.CloseableUtils;

/**
 * Zookeeper(<a>https://zookeeper.apache.org</a>)  Distributed Lock using {@link CuratorFramework}
 * implementation.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public abstract class AbstractZookeeperDistributedLock extends AbstractDistributedLock {

  private final CuratorFramework client;
  private final InterProcessLock lock;

  public AbstractZookeeperDistributedLock(CuratorFramework client,
      InterProcessLock lock) {
    this.client = client;
    this.lock = lock;
  }

  @Override
  public void lockInterruptibly() throws InterruptedException {
    try {
      this.lock.acquire();
    } catch (Exception e) {
      throw new LockInterruptedException("lock error: " + e.getMessage(), e);
    }
  }

  @Override
  public boolean tryLock() {
    try {
      return this.lock.acquire(-1, null);
    } catch (Exception e) {
      throw new LockInterruptedException("try lock error: " + e.getMessage(), e);
    }
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    try {
      return this.lock.acquire(time, unit);
    } catch (Exception e) {
      throw new LockInterruptedException("try lock error: " + e.getMessage(), e);
    }
  }

  @Override
  public void unlock() {
    try {
      this.lock.release();
    } catch (Exception e) {
      throw new LockInterruptedException("unlock error: " + e.getMessage(), e);
    }
  }

  @Override
  public void close() throws IOException {
    CloseableUtils.closeQuietly(client);
  }

}
