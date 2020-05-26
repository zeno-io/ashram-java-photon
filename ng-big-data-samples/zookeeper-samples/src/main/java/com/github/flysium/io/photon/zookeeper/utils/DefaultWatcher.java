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
