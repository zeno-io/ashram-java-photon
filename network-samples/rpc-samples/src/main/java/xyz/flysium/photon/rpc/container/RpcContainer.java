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

package xyz.flysium.photon.rpc.container;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import xyz.flysium.photon.rpc.cluster.ServiceDiscovery;
import xyz.flysium.photon.rpc.invoker.Invoker;
import xyz.flysium.photon.rpc.remoting.Dispatcher;
import xyz.flysium.photon.rpc.remoting.transport.ClientFactory;
import xyz.flysium.photon.rpc.remoting.transport.NettyServer;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class RpcContainer {

  private final NettyServer server;
  private ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8,
      60,
      TimeUnit.SECONDS,
      new ArrayBlockingQueue<>(1024));

  public RpcContainer(int port) {
    server = new NettyServer(this, port);
  }

  public void start() throws InterruptedException, ExecutionException {
    CompletableFuture<Void> future = server.start();
    future.get();
  }

  public ThreadPoolExecutor getExecutor() {
    return executor;
  }

  public void setExecutor(ThreadPoolExecutor executor) {
    this.executor = executor;
  }

  public void registerDiscovery(Class<?> interfaceClass, InetSocketAddress... inetSocketAddress)
      throws InstantiationException, IllegalAccessException {
    ServiceDiscovery.getInstance().register(interfaceClass, inetSocketAddress);
  }

  public void registerBean(Class<?> interfaceClass, Class<?> implClass)
      throws InstantiationException, IllegalAccessException {
    Dispatcher.getInstance().registerBean(interfaceClass, implClass);
  }

  public Invoker getInvoker(String service) throws ClassNotFoundException {
    return Dispatcher.getInstance().getInvoker(service);
  }

  public void shutdown() {
    ClientFactory.getFactory().shutdown();
    server.stop();
  }

}
