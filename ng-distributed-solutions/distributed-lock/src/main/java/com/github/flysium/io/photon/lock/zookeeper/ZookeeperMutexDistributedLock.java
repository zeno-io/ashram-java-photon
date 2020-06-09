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

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.common.PathUtils;

/**
 * a re-entrant Mutex Distributed Lock of Zookeeper(<a>https://zookeeper.apache.org</a>)
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ZookeeperMutexDistributedLock extends AbstractZookeeperDistributedLock {

  public ZookeeperMutexDistributedLock(String groupName, String lockName, CuratorFramework client) {
    this(groupName + lockName, client);
  }

  public ZookeeperMutexDistributedLock(String path, CuratorFramework client) {
    super(client, new InterProcessMutex(client, path));
    PathUtils.validatePath(path);
  }

  @Override
  public boolean isReentrantLock() {
    return true;
  }
}
