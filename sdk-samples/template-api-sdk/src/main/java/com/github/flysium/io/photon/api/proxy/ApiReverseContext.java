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

import com.github.flysium.io.photon.api.ExtendReverseApiRequest;
import com.github.flysium.io.photon.api.ReverseApiRequest;

/**
 * API通知上下文
 *
 * @author Sven Augustus
 */
@SuppressWarnings("PMD.UnusedPrivateField")
class ApiReverseContext<I extends ExtendReverseApiRequest> {

  /* 通知对象 */
  private ReverseApiRequest notifyApiRequest;

  /* 扩展通知对象, 一般为业务扩展 */
  private I request;

  /* 通知URL */
  private String url;

  /* 安全密钥 */
  private String apiKey;

  public ApiReverseContext() {
  }

  public ReverseApiRequest getNotifyApiRequest() {
    return notifyApiRequest;
  }

  public I getRequest() {
    return request;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public static <I extends ExtendReverseApiRequest> Builder<I> builder() {
    return new Builder<I>();
  }

  public static final class Builder<I extends ExtendReverseApiRequest> {

    private final ApiReverseContext<I> apiReverseContext;

    private Builder() {
      apiReverseContext = new ApiReverseContext<I>();
    }

    public Builder<I> notifyApiRequest(ReverseApiRequest notifyApiRequest) {
      apiReverseContext.notifyApiRequest = notifyApiRequest;
      return this;
    }

    public Builder<I> request(I request) {
      apiReverseContext.request = request;
      return this;
    }

    public Builder<I> url(String url) {
      apiReverseContext.setUrl(url);
      return this;
    }

    public Builder<I> apiKey(String apiKey) {
      apiReverseContext.setApiKey(apiKey);
      return this;
    }

    public ApiReverseContext<I> build() {
      return apiReverseContext;
    }
  }
}
