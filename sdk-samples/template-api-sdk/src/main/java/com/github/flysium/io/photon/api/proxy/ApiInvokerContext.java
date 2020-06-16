/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.api.proxy;

import com.github.flysium.io.photon.api.ApiRequest;

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
