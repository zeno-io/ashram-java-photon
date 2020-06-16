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
 * 通知对象
 *
 * @author Sven Augustus
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public final class ReverseApiRequest implements java.io.Serializable {

  private static final long serialVersionUID = -3046643706900808987L;

  private String returnCode;

  private String returnMsg;

  private String appId;

  private String tenantId;

  private String transactionId;

  private String timestamp;

  private String nonceStr;

  private String sign;

  public static ReverseApiRequest success() {
    return success(ReverseApiRequest.builder().build());
  }

  public static ReverseApiRequest success(ReverseApiRequest response) {
    response.setReturnCode(ApiInvoker.SUCCESS);
    response.setReturnMsg("处理成功");
    return response;
  }

  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getReturnMsg() {
    return returnMsg;
  }

  public void setReturnMsg(String returnMsg) {
    this.returnMsg = returnMsg;
  }

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

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private final ReverseApiRequest reverseApiRequest;

    private Builder() {
      reverseApiRequest = new ReverseApiRequest();
    }

    public Builder returnCode(String returnCode) {
      reverseApiRequest.setReturnCode(returnCode);
      return this;
    }

    public Builder returnMsg(String returnMsg) {
      reverseApiRequest.setReturnMsg(returnMsg);
      return this;
    }

    public Builder appId(String appId) {
      reverseApiRequest.setAppId(appId);
      return this;
    }

    public Builder tenantId(String tenantId) {
      reverseApiRequest.setTenantId(tenantId);
      return this;
    }

    public Builder transactionId(String transactionId) {
      reverseApiRequest.setTransactionId(transactionId);
      return this;
    }

    public Builder timestamp(String timestamp) {
      reverseApiRequest.setTimestamp(timestamp);
      return this;
    }

    public Builder nonceStr(String nonceStr) {
      reverseApiRequest.setNonceStr(nonceStr);
      return this;
    }

    public Builder sign(String sign) {
      reverseApiRequest.setSign(sign);
      return this;
    }

    public ReverseApiRequest build() {
      return reverseApiRequest;
    }
  }
}
