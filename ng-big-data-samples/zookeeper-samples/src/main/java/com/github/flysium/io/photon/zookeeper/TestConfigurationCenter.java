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

import com.github.flysium.io.photon.zookeeper.configcenter.MyConfiguration;
import com.github.flysium.io.photon.zookeeper.configcenter.WatchAndCallback;
import com.github.flysium.io.photon.zookeeper.utils.ZookeeperUtils;
import java.io.IOException;
import org.apache.zookeeper.ZooKeeper;

/**
 * 测试配置中心
 *
 * <p>
 * <li>修改 {@link #ZK_ADDRESS} 连接字符串为 可用的 Zookeeper集群 </li>
 * <li>执行 {@link #main} 方法后，zkCli.sh连接，依次执行：</li>
 * <pre>
 * create /AppConf/testConf "hello, config center !"
 * set /AppConf/testConf "new configurations!"
 * set /AppConf/testConf "new configurations  2!"
 * set /AppConf/testConf "new configurations  3!"
 * delete /AppConf/testConf
 * create /AppConf/testConf "hello, config center !"
 * set /AppConf/testConf "new configurations!"
 * delete /AppConf/testConf
 * </pre>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class TestConfigurationCenter {

  public static final String ZK_ADDRESS = "127.0.0.1:2281,127.0.0.1:2282,127.0.0.1:2283";

  /**
   * <p>
   * {@link WatchAndCallback} 提供了两类API:
   * <li>获取存在的配置信息，如果不存在则阻塞等待：{@link WatchAndCallback#awaitExistsReturn()}，{@link
   * WatchAndCallback#awaitExistsReturn(int)}</li>
   * <li>阻塞等待数据变化，一但变化则返回配置信息：{@link WatchAndCallback#awaitDataChangedReturn()}，{@link
   * WatchAndCallback#awaitDataChangedReturn(int)}</li>
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    final ZooKeeper zooKeeper = ZookeeperUtils.newConnection(ZK_ADDRESS + "/AppConf", 3000, 1000);
    // 如果　Zookeeper 是带分组，那么下面的 path 其实真实应该加上分组，也就是 /testConf 其实应该是 /AppConf/testConf
    WatchAndCallback callback = new WatchAndCallback(zooKeeper, "/testConf");

    // 获取存在的配置信息，如果不存在则阻塞等待
    MyConfiguration configuration = callback.awaitExistsReturn();
    System.out.println("Configuration Exists： " + configuration.getConf());
    while (true) {
      // 阻塞等待数据变化，一但变化则返回配置信息
      configuration = callback.awaitDataChangedReturn();
      if (configuration != null) {
        System.out.println("Configuration Changed： " + configuration.getConf());
      }
    }
//    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//      ZookeeperUtils.closeConnection(zooKeeper);
//    }));
  }

}
