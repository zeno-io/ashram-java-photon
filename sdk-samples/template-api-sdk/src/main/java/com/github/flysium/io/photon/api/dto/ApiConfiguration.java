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

package com.github.flysium.io.photon.api.dto;

/**
 * API配置
 *
 * @author Sven Augustus
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public class ApiConfiguration implements java.io.Serializable {

  private static final long serialVersionUID = 8002820123287415378L;

  /* 应用ID */
  private String appId;

  /* API编码 */
  private String apiId;

  private String apiSpecId;

  private String apiInstId;

  /* 通知URL */
  private String url;

  public ApiConfiguration() {
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getApiId() {
    return apiId;
  }

  public void setApiId(String apiId) {
    this.apiId = apiId;
  }

  public String getApiSpecId() {
    return apiSpecId;
  }

  public void setApiSpecId(String apiSpecId) {
    this.apiSpecId = apiSpecId;
  }

  public String getApiInstId() {
    return apiInstId;
  }

  public void setApiInstId(String apiInstId) {
    this.apiInstId = apiInstId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private final ApiConfiguration apiConfiguration;

    private Builder() {
      apiConfiguration = new ApiConfiguration();
    }

    public Builder appId(String appId) {
      apiConfiguration.setAppId(appId);
      return this;
    }

    public Builder apiId(String apiId) {
      apiConfiguration.setApiId(apiId);
      return this;
    }

    public Builder apiSpecId(String apiSpecId) {
      apiConfiguration.setApiSpecId(apiSpecId);
      return this;
    }

    public Builder apiInstId(String apiInstId) {
      apiConfiguration.setApiInstId(apiInstId);
      return this;
    }

    public Builder url(String url) {
      apiConfiguration.setUrl(url);
      return this;
    }

    public ApiConfiguration build() {
      return apiConfiguration;
    }
  }
}
