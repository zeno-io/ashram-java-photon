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

package xyz.flysium.photon.rpc.remoting;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import xyz.flysium.photon.rpc.invoker.Invoker;
import xyz.flysium.photon.rpc.proxy.ProxyFactory;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class Dispatcher {

  private final Map<Class<?>, Invoker> beans = new ConcurrentHashMap<>();

  private Dispatcher() {
  }

  public static Dispatcher getInstance() {
    return DispatcherHolder.INSTANCE;
  }

  public void registerBean(Class<?> interfaceClass, Class<?> implClass)
      throws IllegalAccessException, InstantiationException {
    Invoker invoker = ProxyFactory.getInvoker(implClass);
    beans.putIfAbsent(interfaceClass, invoker);
  }

  public Invoker getInvoker(String service) throws ClassNotFoundException {
    Class<?> interfaceClass = Thread.currentThread().getContextClassLoader()
        .loadClass(service);
    return getInvoker(interfaceClass);
  }

  public Invoker getInvoker(Class<?> interfaceClass) throws ClassNotFoundException {
    return beans.getOrDefault(interfaceClass, null);
  }

  private static class DispatcherHolder {

    private static final Dispatcher INSTANCE = new Dispatcher();
  }


}
