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

package xyz.flysium.photon.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.rpc.cluster.ServiceDiscovery;
import xyz.flysium.photon.rpc.invoker.Invoker;
import xyz.flysium.photon.rpc.remoting.Dispatcher;
import xyz.flysium.photon.rpc.remoting.portocol.RpcRequest;
import xyz.flysium.photon.rpc.remoting.portocol.RpcResponse;
import xyz.flysium.photon.rpc.remoting.transport.ClientFactory;
import xyz.flysium.photon.rpc.remoting.transport.NettyClient;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public final class ProxyFactory {

  private static Logger logger = LoggerFactory.getLogger(ProxyFactory.class);

  private ProxyFactory() {
  }

  public static <T> T getProxy(Class<T> interfaceClass)
      throws InterruptedException, ExecutionException {

    return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
        new Class[]{interfaceClass},
        new InvocationHandler() {

          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcRequest req = new RpcRequest(interfaceClass.getCanonicalName(),
                method.getName(),
                args);

            Invoker invoker = Dispatcher.getInstance().getInvoker(interfaceClass);
            // local (FC)
            if (invoker != null) {
              logger.debug("-> local (FC) ");
              return invoker.invoke(req).getResult();
            }
            // remote (RPC)
            InetSocketAddress inetSocketAddress = ServiceDiscovery.getInstance()
                .choose(interfaceClass);
            NettyClient client = ClientFactory.getFactory().getClient(inetSocketAddress);

            CompletableFuture<RpcResponse> future = client.send(req);
            RpcResponse resp = future.get();
            return resp.getResult();
          }
        });
  }

  public static Invoker getInvoker(Class<?> implClass)
      throws InstantiationException, IllegalAccessException {
    return new Invoker(implClass);
  }

}
