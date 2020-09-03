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
