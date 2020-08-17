/*
 * Copyright 2020 SvenAugustus
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

package xyz.flysium.photon.rpc.cluster;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class ServiceDiscovery {

  private final Map<Class<?>, List<InetSocketAddress>> rpc = new ConcurrentHashMap<>();

  private ServiceDiscovery() {
  }

  public static ServiceDiscovery getInstance() {
    return ServiceDiscoveryHolder.INSTANCE;
  }

  private final ThreadLocalRandom random = ThreadLocalRandom.current();

  public void register(Class<?> interfaceClass, InetSocketAddress... inetSocketAddress)
      throws IllegalAccessException, InstantiationException {
    rpc.putIfAbsent(interfaceClass,
        new ArrayList<InetSocketAddress>(Arrays.asList(inetSocketAddress)));
  }

  public InetSocketAddress choose(Class<?> interfaceClass) {
    List<InetSocketAddress> list = rpc.getOrDefault(interfaceClass, new ArrayList<>());
    // 随机负载均衡
    if (list.isEmpty()) {
      return null;
    }
    if (list.size() == 1) {
      return list.get(0);
    }
    return list.get(random.nextInt(list.size()));
  }

  private static class ServiceDiscoveryHolder {

    private static final ServiceDiscovery INSTANCE = new ServiceDiscovery();
  }
}
