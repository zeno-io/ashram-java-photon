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
