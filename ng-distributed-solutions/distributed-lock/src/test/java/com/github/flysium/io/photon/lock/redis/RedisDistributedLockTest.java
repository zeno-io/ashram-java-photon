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
import com.github.flysium.io.photon.lock.DistributedLockTest;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.config.Config;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class RedisDistributedLockTest extends DistributedLockTest {

  public static final String CONNECT_STRING = "127.0.0.1:6379";
  public static final String CLUSTER_CONNECT_STRING_1 = "127.0.0.1:7001,127.0.0.1:7002,127.0.0.1:7003";
  public static final String CLUSTER_CONNECT_STRING_2 = "127.0.0.1:19101,127.0.0.1:19102,127.0.0.1:19103";

  private AbstractDistributedLock singletonLock;
  private AbstractDistributedLock lock;

  @Before
  public void setUp() {
    Config config = new Config();
    config.useSingleServer().setAddress(CONNECT_STRING);
//    config.useClusterServers().addNodeAddress(CLUSTER_CONNECT_STRING_1.split(","));
//    singletonLock = new RedisDistributedLock("myLock", config);

    Config config1 = new Config();
    config1.useClusterServers().addNodeAddress(CLUSTER_CONNECT_STRING_1.split(","))
        .setScanInterval(5000);
    // it should be another cluster also, this use Sentinel Servers for test case only.
    Config config2 = new Config();
    config2.useSentinelServers().addSentinelAddress(CLUSTER_CONNECT_STRING_2.split(","))
        .setMasterName("master-1");
    lock = new RedisMultiDistributedLock("myMultiLock", config1);
  }

  @After
  public void over() throws IOException {
    if (singletonLock != null) {
      singletonLock.close();
    }
    if (lock != null) {
      lock.close();
    }
  }

  @Test
  public void testSingleServer() throws InterruptedException {
    testIncrement(singletonLock, 1000);
  }

  @Test
  public void test() throws InterruptedException {
    testIncrement(lock, 1);
  }

}