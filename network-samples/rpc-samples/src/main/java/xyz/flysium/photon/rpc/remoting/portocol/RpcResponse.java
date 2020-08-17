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

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class RpcResponse {

  private final long mId;
  private boolean success = true;
  private Object result;
  private Exception e;

  public RpcResponse(long mId) {
    this.mId = mId;
  }

  public RpcResponse(long mId, Object result) {
    this.mId = mId;
    this.result = result;
  }

  public RpcResponse(long mId, Exception e) {
    this.mId = mId;
    this.setException(e);
  }

  public long getId() {
    return mId;
  }

  public boolean isSuccess() {
    return success;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

  public Exception getException() {
    return e;
  }

  public void setException(Exception e) {
    this.e = e;
    this.success = (e == null);
  }

}
