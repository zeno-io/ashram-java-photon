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

package xyz.flysium.photon.rpc.remoting.portocol;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class RpcRequest {

  private static final AtomicLong INVOKE_ID = new AtomicLong(0);

  private final long mId;
  private String service;
  private String method;
  private Object[] args;

  private static long newId() {
    // getAndIncrement() When it grows to MAX_VALUE, it will grow to MIN_VALUE, and the negative can be used as ID
    return INVOKE_ID.getAndIncrement();
  }

  public RpcRequest() {
    this.mId = newId();
  }

  public RpcRequest(String service, String method, Object[] args) {
    this.mId = newId();
    this.service = service;
    this.method = method;
    this.args = args;
  }

  public long getId() {
    return mId;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }

}
