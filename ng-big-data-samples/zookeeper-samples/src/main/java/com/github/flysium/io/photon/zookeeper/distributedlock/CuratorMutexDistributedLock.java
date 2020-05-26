package com.github.flysium.io.photon.zookeeper.distributedlock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * Curator 分布式可重入排它锁
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class CuratorMutexDistributedLock extends AbstractCuratorDistributedLock implements
    DistributedLock {

  public CuratorMutexDistributedLock(CuratorFramework client, String path) {
    super(client, new InterProcessMutex(client, path));
  }

}
