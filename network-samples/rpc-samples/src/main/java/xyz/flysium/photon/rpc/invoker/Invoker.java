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

package xyz.flysium.photon.rpc.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import xyz.flysium.photon.rpc.remoting.portocol.RpcRequest;
import xyz.flysium.photon.rpc.remoting.portocol.RpcResponse;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class Invoker {

  private final Object beanImpl;

  public Invoker(Class<?> implClass) throws IllegalAccessException, InstantiationException {
    this.beanImpl = implClass.newInstance();
  }

  public RpcResponse invoke(RpcRequest req) {
    Object result = null;
    try {
      result = doInvoke(req);
    } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
      return new RpcResponse(req.getId(), e);
    }
    return new RpcResponse(req.getId(), result);
  }

  private Object doInvoke(RpcRequest req)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    String service = req.getService();
    String method = req.getMethod();
    Object[] args = req.getArgs();
    Class<?> clazz = Thread.currentThread().getContextClassLoader()
        .loadClass(service);
    Method clazzMethod = null;
    for (int i = 0; i < clazz.getMethods().length; i++) {
      Method method1 = clazz.getMethods()[i];
      if (method1.getName().equals(method)) {
        clazzMethod = method1;
        break;
      }
    }
    return clazzMethod.invoke(beanImpl, args);
  }

}
