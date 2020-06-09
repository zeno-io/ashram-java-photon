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

package com.github.flysium.io.photon.lock.etcd;

import com.github.flysium.io.photon.lock.AbstractDistributedLock;
import com.github.flysium.io.photon.lock.DistributedLockTest;
import io.etcd.jetcd.Client;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class EtcdDistributedLockTest extends DistributedLockTest {

  public static final String CONNECT_STRING = "http://127.0.0.1:12379,http://127.0.0.1:22379,http://127.0.0.1:32379";

  private AbstractDistributedLock lock;

  @Before
  public void setUp() {
    Client client = Client.builder().endpoints(CONNECT_STRING.split(",")).build();
    lock = new EtcdDistributedLock("myLock", client);
  }

  @After
  public void over() throws IOException {
    if (lock != null) {
      lock.close();
    }
  }

  @Test
  public void test() throws InterruptedException {
    testIncrement(lock, 1000);
  }

}