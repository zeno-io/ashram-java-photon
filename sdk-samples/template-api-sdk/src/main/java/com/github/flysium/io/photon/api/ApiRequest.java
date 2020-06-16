/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
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

package com.github.flysium.io.photon.api;

/**
 * 请求对象
 *
 * @author Sven Augustus
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public class ApiRequest implements java.io.Serializable {

  private static final long serialVersionUID = -228920071542777101L;

  public static final String PARAM_SIGN = "sign";

  public static final String PARAM_SIGN_TYPE = "signType";

  public static final String SIGN_TYPE_HMAC_SHA256 = "HMAC-SHA256";

  public static final String SIGN_TYPE_SHA256 = "SHA256";

  public static final String SIGN_TYPE_MD5 = "MD5";

  private String appId;

  private String tenantId;

  private String transactionId;

  private String timestamp;

  private String nonceStr;

  private String sign;

  private String signType;

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getNonceStr() {
    return nonceStr;
  }

  public void setNonceStr(String nonceStr) {
    this.nonceStr = nonceStr;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getSignType() {
    return signType;
  }

  public void setSignType(String signType) {
    this.signType = signType;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private final ApiRequest apiRequest;

    private Builder() {
      apiRequest = new ApiRequest();
    }

    public Builder appId(String appId) {
      apiRequest.setAppId(appId);
      return this;
    }

    public Builder tenantId(String tenantId) {
      apiRequest.setTenantId(tenantId);
      return this;
    }

    public Builder transactionId(String transactionId) {
      apiRequest.setTransactionId(transactionId);
      return this;
    }

    public Builder timestamp(String timestamp) {
      apiRequest.setTimestamp(timestamp);
      return this;
    }

    public Builder nonceStr(String nonceStr) {
      apiRequest.setNonceStr(nonceStr);
      return this;
    }

    public Builder sign(String sign) {
      apiRequest.setSign(sign);
      return this;
    }

    public Builder signType(String signType) {
      apiRequest.setSignType(signType);
      return this;
    }

    public ApiRequest build() {
      return apiRequest;
    }
  }
}
