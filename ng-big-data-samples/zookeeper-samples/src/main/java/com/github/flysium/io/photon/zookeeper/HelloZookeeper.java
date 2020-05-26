package com.github.flysium.io.photon.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

/**
 * Zookeeper入门
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class HelloZookeeper {

  public static void main(String[] args) throws KeeperException, InterruptedException, IOException {

    final CountDownLatch latch = new CountDownLatch(1);

    // （1）zookeeper 因为有 session概念，因此没有连接池概念
    // （2）zooKeeper 是立马返回的，并不是连接成功才返回，可以通过 CountDownLatch 实现等待连接成功
    // （3）支持连接 FailOver：
    //     如果连接了某zookeeper节点，停止该zookeeper集群节点，客户端会自动 failover
    //     sessionTimeout 3000, 意味着如果此时连接成功后，断开连接，只要3秒内及时 failover 到另外的一个 zookeeper集群节点，涉及的 EPHEMERAL 临时节点 不会被自动删除
    // （4）Zookeeper 连接的 Watch 是 Session 相关的（也叫 Default Watch），与 path, node 没有关系
    final ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2281,127.0.0.1:2282,127.0.0.1:2283", 3000,
        new Watcher() {
          @Override
          public void process(WatchedEvent watchedEvent) {
            KeeperState state = watchedEvent.getState();
            EventType type = watchedEvent.getType();

            System.out.println("Default Watch for Session: " + watchedEvent);

            switch (state) {
              case Unknown:
                break;
              case Disconnected:
                break;
              case NoSyncConnected:
                break;
              case SyncConnected:
//                System.out.println("reconnected.......");
                if (latch.getCount() == 1) {
                  latch.countDown();
                }
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

            switch (type) {
              case None:
                break;
              case NodeCreated:
                break;
              case NodeDeleted:
                break;
              case NodeDataChanged:
                break;
              case NodeChildrenChanged:
                break;
            }
          }
        });

    latch.await();
    States state = zooKeeper.getState();
    System.out.println("Zookeeper state: " + state);
    switch (state) {
      case CONNECTING:
        break;
      case ASSOCIATING:
        break;
      case CONNECTED:
        break;
      case CONNECTEDREADONLY:
        break;
      case CLOSED:
        break;
      case AUTH_FAILED:
        break;
      case NOT_CONNECTED:
        break;
    }

    Stat stat = zooKeeper.exists("/zk_test", false);
    if (stat == null) {
      // 创建一个 EPHEMERAL 临时节点
      String pathName = zooKeeper
          .create("/zk_test", "hello zookeeper".getBytes(), Ids.OPEN_ACL_UNSAFE,
              CreateMode.EPHEMERAL);
      System.out.println("Create Node: " + pathName);
    }

    // 同步查看节点，并建立 Watch， 如果节点数据有变动，则执行 Watch 逻辑
//    byte[] data = zooKeeper.getData("/zk_test", new Watcher() {
//      @Override
//      public void process(WatchedEvent watchedEvent) {
//        System.out.println("Watch for Node: /zk_test, event: " + watchedEvent);
//        // 重新注册，以便监视，否则一次修改触发 Watch 后，Watch 就会失效
//        // getData(String path, boolean watch, Stat stat) 如果 watch = true, 则触发的是 Default Watch， 不是当前的节点的 Watch
//        zooKeeper.getData("/zk_test", this, stat);
//      }
//    }, stat);
//    System.out.println("Get /zk_test : " + new String(data));

    // 异步查看节点，并建立 Watch， 如果节点数据有变动，则执行 Watch 逻辑
    DataCallback dataCallback = new DataCallback() {
      @Override
      public void processResult(int rc, String path, Object ctx, byte[] data,
          Stat stat) {
        System.out.println("Get /zk_test : " + new String(data));
      }
    };
    Object ctx = "something";
    zooKeeper.getData("/zk_test", new Watcher() {
      @Override
      public void process(WatchedEvent event) {
        System.out.println("Watch for Node: /zk_test, event: " + event);
        // 重新注册，以便监视，否则一次修改触发 Watch 后，Watch 就会失效
        // getData(String path, boolean watch, Stat stat) 如果 watch = true, 则触发的是 Default Watch， 不是当前的节点的 Watch
        // zooKeeper.getData("/zk_test", true, stat);
        zooKeeper.getData("/zk_test", this, dataCallback, ctx);
      }
    }, dataCallback, ctx);

    // 修改节点，触发 Watch
    Stat stat1 = zooKeeper.setData("/zk_test", "update 1".getBytes(), 0);
    // 第二次修改节点
    Stat stat2 = zooKeeper.setData("/zk_test", "update 2 ".getBytes(), stat1.getVersion());

    TimeUnit.SECONDS.sleep(30000);
  }

}
