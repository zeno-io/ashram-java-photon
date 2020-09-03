/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
