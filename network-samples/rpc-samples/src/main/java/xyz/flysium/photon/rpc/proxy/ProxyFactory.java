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
