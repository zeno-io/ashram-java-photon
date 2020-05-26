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

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.ZooKeeper;

/**
 * Zookeeper 工具类
 *
 * @author Sven Augustus
 * @version 1.0
 */
public final class ZookeeperUtils {

  private ZookeeperUtils() {
  }

  /**
   * 创建并连接 Zookeeper
   *
   * @param connectString  连接Zookeeper字符串，一般为Zookeeper的IP:PORT，以,连接，如 127.0.0.1:2281,127.0.0.1:2282,127.0.0.1:2283
   * @param sessionTimeout Session超时时间，单位毫秒
   * @param connectTimeout 连接超时时间，单位毫秒
   * @return Zookeeper客户端实例，已连接
   * @throws IOException 如果连接异常则抛出
   */
  public static ZooKeeper newConnection(String connectString, int sessionTimeout,
      int connectTimeout) throws IOException {
    final CountDownLatch latch = new CountDownLatch(1);
    DefaultWatcher watcher = new DefaultWatcher(latch);
    ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
    try {
      if (connectTimeout > 0) {
        latch.await(connectTimeout, TimeUnit.MILLISECONDS);
      } else {
        latch.await();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return zooKeeper;
  }

  /**
   * 关闭连接 Zookeeper
   *
   * @param zooKeeper Zookeeper实例，已连接
   */
  public static void closeConnection(ZooKeeper zooKeeper) {
    if (zooKeeper == null) {
      return;
    }
    try {
      if (zooKeeper.getState().isConnected()) {
        zooKeeper.close();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 创建 CuratorFramework 客户端 并连接 Zookeeper
   *
   * @param connectString  连接Zookeeper字符串，一般为Zookeeper的IP:PORT，以,连接，如 127.0.0.1:2281,127.0.0.1:2282,127.0.0.1:2283
   * @param sessionTimeout Session超时时间，单位毫秒
   * @param connectTimeout 连接超时时间，单位毫秒
   * @return CuratorFramework 客户端，已连接
   * @throws IOException 如果连接异常则抛出
   */
  public static CuratorFramework newCuratorClient(String connectString, int sessionTimeout,
      int connectTimeout) throws IOException, InterruptedException {
    // TODO
    // 初始休眠时间为 1000ms, 最大重试次数为 3
    RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
    // 创建一个客户端, sessionTimeout(ms)为 session 超时时间, connectTimeout(ms)为链接超时时间
    CuratorFramework client = CuratorFrameworkFactory
        .newClient(connectString, sessionTimeout, connectTimeout, retry);
    client.start();
    return client;
  }

  /**
   * 关闭连接 CuratorFramework 客户端
   *
   * @param client CuratorFramework 客户端
   */
  public static void closeCuratorClient(CuratorFramework client) {
    if (client == null) {
      return;
    }
    try {
      CloseableUtils.closeQuietly(client);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
