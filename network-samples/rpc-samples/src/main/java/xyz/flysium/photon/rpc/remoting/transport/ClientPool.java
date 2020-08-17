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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class ClientPool {

  private final NettyClient[] clients;
  private final Object[] locks;

  public ClientPool(int poolSize) {
    clients = new NettyClient[poolSize];
    locks = new Object[poolSize];
    for (int i = 0; i < locks.length; i++) {
      locks[i] = new Object();
    }
  }

  public NettyClient getOrCreateClient(int index, InetSocketAddress inetSocketAddress)
      throws ExecutionException, InterruptedException {
    if (clients[index] != null && clients[index].isActive()) {
      return clients[index];
    }
    synchronized (locks[index]) {
      clients[index] = new NettyClient(inetSocketAddress.getHostName(),
          inetSocketAddress.getPort());
      CompletableFuture<Void> future = clients[index].start();
      future.get();
      return clients[index];
    }
  }

  public void shutdownAll() {
    for (int i = 0; i < clients.length; i++) {
      synchronized (locks[i]) {
        clients[i].stop();
      }
    }
  }

}
