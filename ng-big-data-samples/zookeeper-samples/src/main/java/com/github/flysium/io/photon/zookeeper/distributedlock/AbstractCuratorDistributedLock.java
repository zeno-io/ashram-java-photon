/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
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

package com.github.flysium.io.photon.zookeeper.distributedlock;

import com.github.flysium.io.photon.zookeeper.utils.ZookeeperUtils;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;

/**
 * Curator（ZooKeeper） 分布式锁
 *
 * @author Sven Augustus
 * @version 1.0
 */
public abstract class AbstractCuratorDistributedLock implements DistributedLock {

  /**
   * CuratorFramework 客户端实例
   */
  private final CuratorFramework client;
  /**
   * 锁的节点路径，如 /testLock/app1/lock
   */
  private final InterProcessLock lock;

  AbstractCuratorDistributedLock(CuratorFramework client, InterProcessLock lock) {
    this.client = client;
    this.lock = lock;
  }

  @Override
  public void lock() throws Exception {
    lock.acquire();
  }

  @Override
  public boolean tryLock(int timeout) throws Exception {
    return lock.acquire(timeout, TimeUnit.MILLISECONDS);
  }

  @Override
  public void unlock() throws Exception {
    lock.release();
  }

  @Override
  public void close() throws IOException {
    ZookeeperUtils.closeCuratorClient(client);
  }

}
