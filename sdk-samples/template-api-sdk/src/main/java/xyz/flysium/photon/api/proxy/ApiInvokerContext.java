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

package xyz.flysium.photon.api.proxy;

import xyz.flysium.photon.api.ApiRequest;

/**
 * API业务调用请求上下文
 *
 * @author Sven Augustus
 */

@SuppressWarnings("PMD.UnusedPrivateField")
class ApiInvokerContext<I extends ApiRequest> {

  /* 请求对象 */
  private I request;

  /* 是否安全认证 */
  private boolean signatureCheckEnabled;

  /* 安全密钥 */
  private String apiKey;

  public ApiInvokerContext() {
  }

  public I getRequest() {
    return request;
  }

  public boolean isSignatureCheckEnabled() {
    return signatureCheckEnabled;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public static <I extends ApiRequest> Builder<I> builder() {
    return new Builder<I>();
  }

  public static final class Builder<I extends ApiRequest> {

    private final ApiInvokerContext<I> apiInvokerContext;

    private Builder() {
      apiInvokerContext = new ApiInvokerContext<I>();
    }

    public Builder<I> request(I request) {
      apiInvokerContext.request = request;
      return this;
    }

    public Builder<I> signatureCheckEnabled(boolean signatureCheckEnabled) {
      apiInvokerContext.signatureCheckEnabled = signatureCheckEnabled;
      return this;
    }

    public Builder<I> apiKey(String apiKey) {
      apiInvokerContext.setApiKey(apiKey);
      return this;
    }

    public ApiInvokerContext<I> build() {
      return apiInvokerContext;
    }
  }
}
