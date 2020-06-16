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
 * 应用安全
 *
 * @author Sven Augustus
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public class AppSecret implements java.io.Serializable {

  private static final long serialVersionUID = -1567667442652291007L;

  /* 应用ID */
  private String appId;

  /* 是否安全认证 */
  private boolean signatureCheckEnabled;

  /* 安全密钥 */
  private String appKey;

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public boolean isSignatureCheckEnabled() {
    return signatureCheckEnabled;
  }

  public void setSignatureCheckEnabled(boolean signatureCheckEnabled) {
    this.signatureCheckEnabled = signatureCheckEnabled;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private final AppSecret appSecret;

    private Builder() {
      appSecret = new AppSecret();
    }

    public Builder appId(String appId) {
      appSecret.setAppId(appId);
      return this;
    }

    public Builder signatureCheckEnabled(boolean signatureCheckEnabled) {
      appSecret.setSignatureCheckEnabled(signatureCheckEnabled);
      return this;
    }

    public Builder appKey(String appKey) {
      appSecret.setAppKey(appKey);
      return this;
    }

    public AppSecret build() {
      return appSecret;
    }
  }

}
