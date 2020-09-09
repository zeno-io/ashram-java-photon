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
