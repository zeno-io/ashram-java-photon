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
