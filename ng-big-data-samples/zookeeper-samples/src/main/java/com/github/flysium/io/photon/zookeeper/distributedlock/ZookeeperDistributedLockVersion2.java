package com.github.flysium.io.photon.zookeeper.distributedlock;

import java.util.Collections;
import java.util.List;
import org.apache.zookeeper.AsyncCallback.Children2Callback;
import org.apache.zookeeper.AsyncCallback.StatCallback;
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
 * 分布式锁版本2
 * <p>
 * 创建一个 EPHEMERAL_SEQUENTIAL 临时节点
 * <br>
 * 最小顺序的节点持有者获得锁，该节点可以由持有者主动释放，或持有者连接断开后Zookeeper自动释放
 * <br>
 * 后继顺序节点 Watch 前一个顺序节点的 删除事件，判定获得锁
 *
 * <p>
 * 缺点：在遍历子节点时会有一定的 网络IO 开销
 * </p>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ZookeeperDistributedLockVersion2 extends AbstractZookeeperDistributedLock implements
    DistributedLock, Watcher, StringCallback,
    StatCallback, Children2Callback {

  /**
   * 当前持有的顺序节点的路径，如 /&lt;parentPath&gt;/&lt;node&gt;0000000001
   */
  private String currentSequentialPath;
  /**
   * 当前持有的顺序节点的名称，如 &lt;node&gt;0000000001
   */
  private String currentSequentialNodeName;

  public ZookeeperDistributedLockVersion2(ZooKeeper zookeeper, String path) {
    super(zookeeper, path, true);
  }

  @Override
  protected void create() throws InterruptedException {
    // register StringCallback
    zookeeper.create(path, "anything you want".getBytes(),
        Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
        this, context);
  }

  @Override
  protected void delete() throws InterruptedException, KeeperException {
    zookeeper.delete(currentSequentialPath, -1);
  }


  // Watcher
  @Override
  public void process(WatchedEvent event) {
    logger.debug("Path Watcher: {}， event: {} ", this.path, event);
    if (EventType.NodeDeleted == event.getType()) {
      logger.debug("Thread: {}, Path : {} , Try to scan and lock ...",
          context.getCurrentHoldThreadName(), path);
      // 前驱顺序节点释放，检查当前节点是否第一个顺序节点
      // register Watcher , Children2Callback （当 Watch 的前驱顺序节点释放，需要重新获取存活的顺序节点）
      // 特别注意 watch = false, 不需要 监视所有的孩子节点
      zookeeper.getChildren(this.parentPath, false, this, context);
    }
  }

  // StringCallback
  @Override
  public void processResult(int rc, String path, Object ctx, String name) {
    logger.debug("Path StringCallback: {}， result path: {} ", this.path, path);
    if (Code.OK.intValue() == rc) {
      // 创建成功
      if (name != null) {
        this.currentSequentialPath = name;
        this.currentSequentialNodeName = currentSequentialPath
            .substring(currentSequentialPath.indexOf(parentPath) + parentPath.length() + 1);

        logger.info(
            "Thread: {} , Path: {} is created. Try to scan the Previous from parent: {}",
            context.getCurrentHoldThreadName(),
            currentSequentialPath, this.parentPath);

        // register Watcher , Children2Callback（重新获取存活的顺序节点，决策是最小顺序节点，还是需要继续 Watch 前驱节点）
        // 特别注意 watch = false, 不需要 监视所有的孩子节点
        zookeeper.getChildren(this.parentPath, false, this, ctx);
      }
    }
  }

  // Children2Callback
  @Override
  public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
    if (children == null || children.isEmpty()) {
      return;
    }
    // 取出当前的孩子节点
    Collections.sort(children);

    PredicateLockResult predicateLockResult = getsTheLock(children);
    // 最小顺序的节点持有者获得锁
    if (predicateLockResult.isGetsTheLock()) {
      logger.info("Thread: {}, Path : {} , Success to lock !",
          context.getCurrentHoldThreadName(), currentSequentialPath);
      try {
        Stat s = new Stat();
        // 读取数据
        zookeeper.getData(currentSequentialPath, false, s);
        // 解除阻塞
        returnLock(s);
      } catch (KeeperException | InterruptedException e) {
        logger.error("Read Data Error：" + currentSequentialPath, e);
      }
      return;
    }
    // 后继顺序节点 Watch 前一个顺序节点的 删除事件，判定获得锁
    final String prevSequentialPath = getPrevSequentialPath(predicateLockResult.getPathToWatch());

    logger.info(
        "Thread: {}, Path : {} , Failed to lock, try to watch Previous : {} ! ",
        context.getCurrentHoldThreadName(),
        currentSequentialPath, prevSequentialPath);

    // register Watcher , StatCallback (为防止 Watch 前驱顺序节点失败，通过 StatCallback 恢复)
    zookeeper.exists(prevSequentialPath, this, this, ctx);
  }


  // StatCallback
  @Override
  public void processResult(int rc, String path, Object ctx, Stat stat) {
    // 为防止 Watch 前驱顺序节点失败，通过 StatCallback 恢复
    if (stat == null) {
      logger.warn(
          "Thread: {}, Path : {} , Previous (not exists): {}, Try to recover watch.",
          context.getCurrentHoldThreadName(), this.path, path);
      // register Watcher , Children2Callback（重新获取存活的顺序节点，决策是最小顺序节点，还是需要继续 Watch 前驱节点）
      // 特别注意 watch = false, 不需要 监视所有的孩子节点
      zookeeper.getChildren(this.parentPath, false, this, ctx);
    } else {
      logger.debug("Thread: {}, Path : {} , Previous (existed): {}, Skip.",
          context.getCurrentHoldThreadName(), this.path, path);
    }
  }

  private PredicateLockResult getsTheLock(List<String> children) {
    int ourIndex = children.indexOf(currentSequentialNodeName);

    if (ourIndex < 0) {
      throw new IllegalStateException("Sequential path not found: " + currentSequentialNodeName);
    }

    int maxLeases = 1;
    boolean getsTheLock = ourIndex < maxLeases;
    String pathToWatch = getsTheLock ? null : children.get(ourIndex - maxLeases);

    return new PredicateLockResult(getsTheLock, pathToWatch);
  }

  private String getPrevSequentialPath(String prevSequentialNodeName) {
    if ("/".equals(this.parentPath)) {
      return this.parentPath + prevSequentialNodeName;
    }
    return this.parentPath + "/" + prevSequentialNodeName;
  }

}
