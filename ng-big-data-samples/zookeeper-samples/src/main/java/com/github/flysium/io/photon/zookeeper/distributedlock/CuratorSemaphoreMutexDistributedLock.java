package com.github.flysium.io.photon.zookeeper.distributedlock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

/**
 * Curator 分布式排它锁
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class CuratorSemaphoreMutexDistributedLock extends AbstractCuratorDistributedLock implements
    DistributedLock {

  public CuratorSemaphoreMutexDistributedLock(CuratorFramework client, String path) {
    super(client, new InterProcessSemaphoreMutex(client, path));
  }

}
