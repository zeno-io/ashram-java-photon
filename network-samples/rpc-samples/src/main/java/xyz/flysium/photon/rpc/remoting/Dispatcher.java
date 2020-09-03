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
