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
import com.github.flysium.io.photon.lock.DistributedLockTest;
import java.io.IOException;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class ZookeeperDistributedLockTest extends DistributedLockTest {

  public static final String CONNECT_STRING = "127.0.0.1:2281,127.0.0.1:2282,127.0.0.1:2283";

  private AbstractDistributedLock nonReentrantLock;
  private AbstractDistributedLock lock;

  @Before
  public void setUp() {
    RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
    CuratorFramework client = CuratorFrameworkFactory.newClient(
        CONNECT_STRING, 3000, 1000, retry);
    client.start();
    nonReentrantLock = new ZookeeperSemaphoreDistributedLock("/", "myNonReentrantLock", client);
    lock = new ZookeeperMutexDistributedLock("/", "myLock", client);
  }

  @After
  public void over() throws IOException {
    if (nonReentrantLock != null) {
      nonReentrantLock.close();
    }
    if (lock != null) {
      lock.close();
    }
  }

  @Test
  public void testNonReentrantLock() throws InterruptedException {
    testIncrement(nonReentrantLock, 1);
  }

  @Test
  public void test() throws InterruptedException {
    testIncrement(lock, 1000);
  }

}