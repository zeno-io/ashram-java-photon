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

package xyz.flysium.photon.rpc.remoting.transport;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import xyz.flysium.photon.rpc.Constant;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class ClientFactory {

  private final int poolSize;
  private final Map<String, ClientPool> pools = new ConcurrentHashMap<>();
  private final ThreadLocalRandom random = ThreadLocalRandom.current();

  public static ClientFactory getFactory() {
    return ClientFactoryHolder.INSTANCE;
  }

  private ClientFactory(int poolSize) {
    this.poolSize = poolSize;
  }

  public synchronized NettyClient getClient(InetSocketAddress inetSocketAddress)
      throws InterruptedException, ExecutionException {
    String key = keyFor(inetSocketAddress);
    ClientPool clientPool = pools.get(key);
    if (clientPool == null) {
      synchronized (this) {
        clientPool = pools.get(key);
        if (clientPool == null) {
          clientPool = new ClientPool(poolSize);
          pools.putIfAbsent(key, clientPool);
        }
      }
    }

    int i = random.nextInt(poolSize);

    return clientPool.getOrCreateClient(i, inetSocketAddress);
  }

  public void shutdown() {
    for (Entry<String, ClientPool> entry : pools.entrySet()) {
      entry.getValue().shutdownAll();
    }
  }

  private String keyFor(InetSocketAddress inetSocketAddress) {
    return inetSocketAddress.getHostName() + ":" + inetSocketAddress.getPort();
  }

  private static class ClientFactoryHolder {

    private static final ClientFactory INSTANCE = new ClientFactory(Constant.CONSUMER_POOL_SIZE);
  }

}
