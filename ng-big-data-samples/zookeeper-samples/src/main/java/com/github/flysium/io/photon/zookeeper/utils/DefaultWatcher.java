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

package com.github.flysium.io.photon.zookeeper.utils;

import com.github.flysium.io.photon.zookeeper.distributedlock.DistributedLock;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default Watcher for Session
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class DefaultWatcher implements Watcher {

  final Logger logger = LoggerFactory.getLogger(DistributedLock.class);

  private CountDownLatch latch;

  public DefaultWatcher(CountDownLatch latch) {
    this.latch = latch;
  }

  @Override
  public void process(WatchedEvent event) {
    logger.debug("Default Watch for Session: " + event);
    switch (event.getState()) {
      case Unknown:
        break;
      case Disconnected:
        break;
      case NoSyncConnected:
        break;
      case SyncConnected:
        latch.countDown();
        latch = new CountDownLatch(1);
        break;
      case AuthFailed:
        break;
      case ConnectedReadOnly:
        break;
      case SaslAuthenticated:
        break;
      case Expired:
        break;
    }
  }

}
