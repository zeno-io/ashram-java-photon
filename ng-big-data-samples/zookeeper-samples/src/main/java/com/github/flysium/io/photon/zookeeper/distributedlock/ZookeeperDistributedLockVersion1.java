package com.github.flysium.io.photon.zookeeper.distributedlock;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 分布式锁版本1
 * <p>
 * 抢占创建一个 EPHEMERAL 临时节点
 * <br>
 * 该节点可以由持有者主动释放，或持有者连接断开后Zookeeper自动释放
 * <br>
 * 这样其他线程通过 Watch 监视到该节点的 删除事件，就会再次抢占创建
 *
 * <p>
 * 缺点：
 * <li>惊群效应：并发量大会造成节点删除时大量的泛洪 Watch 回调 和 抢占创建</li>
 * <li>属于不公平锁</li>
 * </p>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ZookeeperDistributedLockVersion1 extends AbstractZookeeperDistributedLock implements
    DistributedLock, Watcher, StringCallback,
    DataCallback {

  public ZookeeperDistributedLockVersion1(ZooKeeper zookeeper, String path) {
    super(zookeeper, path, false);
  }

  @Override
  protected void create() {
    logger.debug("Thread: {}, Path : {} , Try to lock ...",
        context.getCurrentHoldThreadName(), path);
    // register StringCallback
    zookeeper.create(path, "anything you want".getBytes(),
        Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
        this, "ctx anything you want");
  }

  @Override
  protected void delete() throws KeeperException, InterruptedException {
    zookeeper.delete(path, -1);
  }

  // Watcher
  @Override
  public void process(WatchedEvent event) {
    logger.debug("Path Watcher: {}， event: {} ", this.path, event);
    if (EventType.NodeDeleted == event.getType()) {
      // 其他人释放锁了，可以继续抢占创建
      create();
    }
  }

  // StringCallback
  @Override
  public void processResult(int rc, String path, Object ctx, String name) {
    logger.debug("Path StringCallback: {}， result path: {} ", this.path, path);

    PredicateLockResult predicateLockResult = getsTheLock(rc);
    if (predicateLockResult.isGetsTheLock()) {
      logger.info("Thread: {}, Path : {} , Success to lock !",
          context.getCurrentHoldThreadName(), this.path);
      // 抢占创建成功，取出数据
      try {
        Stat s = new Stat();
        // 读取数据
        zookeeper.getData(path, false, s);
        // 解除阻塞
        returnLock(s);
      } catch (KeeperException | InterruptedException e) {
        logger.error("Read Data Error：" + path, e);
      }
      return;
    }
    logger.debug("Thread: {}, Path : {} , Failed to lock, register the Watcher.",
        context.getCurrentHoldThreadName(), this.path);
    // 抢占创建不成功，继续监视 Watch
    // register Watcher , DataCallback (为防止锁已经释放了)
    zookeeper.getData(predicateLockResult.getPathToWatch(), this, this, new Stat());
  }

  // DataCallback
  @Override
  public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
    logger.debug("Path DataCallback: {}， result stat: {} ", this.path, stat);
    if (stat == null) {
      // register StringCallback
      create();
    }
  }

  private PredicateLockResult getsTheLock(int rc) {
    boolean getsTheLock = Code.OK.intValue() == rc;
    String pathToWatch = getsTheLock ? null : this.path;

    return new PredicateLockResult(getsTheLock, pathToWatch);
  }

}
