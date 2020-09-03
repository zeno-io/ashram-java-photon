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
