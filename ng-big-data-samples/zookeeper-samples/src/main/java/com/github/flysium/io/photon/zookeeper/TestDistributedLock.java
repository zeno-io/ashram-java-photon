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

package com.github.flysium.io.photon.zookeeper;

import com.github.flysium.io.photon.zookeeper.distributedlock.CuratorMutexDistributedLock;
import com.github.flysium.io.photon.zookeeper.distributedlock.DistributedLock;
import com.github.flysium.io.photon.zookeeper.distributedlock.ZookeeperDistributedLockVersion1;
import com.github.flysium.io.photon.zookeeper.distributedlock.ZookeeperDistributedLockVersion2;
import com.github.flysium.io.photon.zookeeper.utils.ZookeeperUtils;
import java.io.Closeable;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试分布式锁
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class TestDistributedLock {

  public static final String ZK_ADDRESS = "127.0.0.1:2281,127.0.0.1:2282,127.0.0.1:2283";
  public static final String GROUP_NAME = "/testLock";
  // 测试的线程数
  public static final int THREAD_NUM = 1000;
  // 模拟业务逻辑处理
  private static final int HANDLE_TIMEOUT_IN_MILLI_SECONDS = 0;
  // 分布式锁的版本
  private static final int CASE_CHOOSE = 2;
  // 打印每个线程的耗时
  private static final boolean PRINT_STAT_EACH_TREAD = true;

  public static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(4, 4, 60,
      TimeUnit.SECONDS,
      new ArrayBlockingQueue<>(THREAD_NUM), new ThreadFactory() {
    private final AtomicLong l = new AtomicLong(1);

    @Override
    public Thread newThread(Runnable r) {
      return new Thread(r, "thread-" + l.getAndIncrement());
    }
  }, new CallerRunsPolicy());

  private static final Logger logger = LoggerFactory.getLogger(TestDistributedLock.class);

  public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
    try {
      final ZooKeeper zooKeeper = getZooKeeper();
      // 创建 /testLock
      createIfNotExists(zooKeeper, "/");
      createIfNotExists(zooKeeper, "/version1");
      createIfNotExists(zooKeeper, "/version2");
      createIfNotExists(zooKeeper, "/curatorMutex");
      createIfNotExists(zooKeeper, "/curatorSemaphoreMutex");
    } catch (Exception e) {
      e.printStackTrace();
    }
    switch (CASE_CHOOSE) {
      case 1:
        // 版本1：抢占创建一个 EPHEMERAL 临时节点，其他线程同时 Watch 这个节点的释放
        System.out.println(
            " ZookeeperDistributedLockVersion1 ------> " + testDistributedLock(() -> {
              return new ZookeeperDistributedLockVersion1(getZooKeeper(),
                  "/version1/lock");
            }, false) + " ms");
        break;
      case 2:
        // 版本2：创建一个 EPHEMERAL_SEQUENTIAL 临时节点，最小顺序的获得锁，后继顺序节点 Watch 前一个顺序节点的释放
        System.out.println(
            " ZookeeperDistributedLockVersion2 ------> " + testDistributedLock(() -> {
              return new ZookeeperDistributedLockVersion2(getZooKeeper(),
                  "/version2/lock");
            }, true) + " ms");
        break;
      case 3:
        // Curator 分布式排它锁
        System.out.println(
            " CuratorMutexDistributedLock ------> " + testDistributedLock(() -> {
              return new CuratorMutexDistributedLock(getCuratorFramework(),
                  "/curatorMutex/lock");
            }, true) + " ms");
        break;
      case 4:
        // Curator 分布式可重入排它锁
        System.out.println(
            " CuratorMutexDistributedLock ------> " + testDistributedLock(() -> {
              return new CuratorMutexDistributedLock(getCuratorFramework(),
                  "/curatorMutex/lock");
            }, true) + " ms");
        break;
      default:
        break;
    }
  }

  private static long testDistributedLock(
      final Supplier<DistributedLock> newDistributedLock,
      boolean supportReentrantLock)
      throws InterruptedException {
    List<Callable<Closeable>> callableList = new ArrayList<>(THREAD_NUM);

    DistributedLock[] locks = new DistributedLock[THREAD_NUM];
    for (int i = 0; i < THREAD_NUM; i++) {
      locks[i] = newDistributedLock.get();
    }
    final StatResult[] timeSpends = new StatResult[THREAD_NUM];

    for (int i = 0; i < THREAD_NUM; i++) {
      final int index = i;
      callableList.add(() -> {
        long start = Instant.now().toEpochMilli();
        final DistributedLock lock = locks[index];
        // 阻塞获得锁
        try {
          lock.lock();
          logger.info(Instant.now().toEpochMilli() + "--------------------->"
              + Thread.currentThread().getName() + " acquire lock !");
          if (supportReentrantLock) {
            lock.lock();
            logger.info(Instant.now().toEpochMilli() + "--------------------->"
                + Thread.currentThread().getName() + " acquire lock again!");
          }
        } catch (Throwable e) {
          e.printStackTrace();
        }
        try {
          // 模拟业务逻辑处理
          if (HANDLE_TIMEOUT_IN_MILLI_SECONDS > 0) {
            TimeUnit.MILLISECONDS.sleep(HANDLE_TIMEOUT_IN_MILLI_SECONDS);
          }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          // 主动释放锁
          if (supportReentrantLock) {
            lock.unlock();
            logger.info(Instant.now().toEpochMilli() + "--------------------->"
                + Thread.currentThread().getName() + " release lock first!");
          }
          lock.unlock();
          logger.info(Instant.now().toEpochMilli() + "--------------------->"
              + Thread.currentThread().getName() + " release lock !");

          long end = Instant.now().toEpochMilli();
          if (PRINT_STAT_EACH_TREAD) {
            timeSpends[index] = new StatResult(Thread.currentThread().getName(), start, end);
          }
        }

        return lock;
      });
    }
    long start = Instant.now().toEpochMilli();
    // 全部执行并等待全部完成
    final List<Future<Closeable>> closeableFutures = EXECUTOR.invokeAll(callableList);
    long total = Instant.now().toEpochMilli() - start;
    // 关闭线程池通道，不接受新任务
    System.out.println("--------------------->" + total + "ms");

    // 关闭 zookeeper 连接
    EXECUTOR.submit(() -> {
      closeableFutures.forEach(f ->
      {
        try {
          if (f.get() != null) {
            f.get().close();
          }
        } catch (InterruptedException | ExecutionException | IOException e) {
          logger.error(e.getMessage(), e);
        }
      });
    });
    EXECUTOR.shutdown();

    if (PRINT_STAT_EACH_TREAD) {
      Arrays.stream(timeSpends).sorted().forEach(System.out::println);
    }

    return total;
  }

  private static void createIfNotExists(ZooKeeper zooKeeper, String path)
      throws KeeperException, InterruptedException {
    if (zooKeeper.exists(path, false) == null) {
      zooKeeper.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
  }

  private static ZooKeeper getZooKeeper() {
    try {
      return ZookeeperUtils.newConnection(ZK_ADDRESS + GROUP_NAME, 3000, 1000);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  private static CuratorFramework getCuratorFramework() {
    try {
      return ZookeeperUtils.newCuratorClient(ZK_ADDRESS + GROUP_NAME, 3000, 1000);
    } catch (IOException | InterruptedException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  static class StatResult implements Comparable<StatResult> {

    private final String threadName;

    private final long startEpochMillis;

    private final long endEpochMillis;

    public StatResult(String threadName, long startEpochMillis, long endEpochMillis) {
      this.threadName = threadName;
      this.startEpochMillis = startEpochMillis;
      this.endEpochMillis = endEpochMillis;
    }

    public String getThreadName() {
      return threadName;
    }

    public long getStartEpochMillis() {
      return startEpochMillis;
    }

    public long getEndEpochMillis() {
      return endEpochMillis;
    }

    @Override
    public String toString() {
      return "StatResult{" +
          "threadName='" + threadName + '\'' +
          ", startEpochMillis=" + startEpochMillis +
          ", endEpochMillis=" + endEpochMillis +
          ", total=" + (endEpochMillis - startEpochMillis) +
          '}';
    }

    @Override
    public int compareTo(StatResult o) {
      if (o == null) {
        return -1;
      }
      return (int) ((this.endEpochMillis
          - this.startEpochMillis) - (o.endEpochMillis - o.startEpochMillis));
    }
  }

}
