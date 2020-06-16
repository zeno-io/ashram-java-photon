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
 * 响应对象
 *
 * @author Sven Augustus
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public final class ApiResponse implements java.io.Serializable {

  private static final long serialVersionUID = -5278688902213849773L;

  private String returnCode;

  private String returnMsg;

  private String appId;

  private String tenantId;

  /* 接口日志流水ID */
  private String apiTransId;

  private String timestamp;

  private String nonceStr;

  private String sign;

  private String signType;

  private String resultCode;

  private String errCode;

  private String errCodeDes;

  public static ApiResponse success() {
    return success(ApiResponse.builder().build());
  }

  public static ApiResponse success(ApiResponse response) {
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

  public String getApiTransId() {
    return apiTransId;
  }

  public void setApiTransId(String apiTransId) {
    this.apiTransId = apiTransId;
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

  public String getResultCode() {
    return resultCode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public String getErrCode() {
    return errCode;
  }

  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }

  public String getErrCodeDes() {
    return errCodeDes;
  }

  public void setErrCodeDes(String errCodeDes) {
    this.errCodeDes = errCodeDes;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private final ApiResponse apiResponse;

    private Builder() {
      apiResponse = new ApiResponse();
    }

    public Builder returnCode(String returnCode) {
      apiResponse.setReturnCode(returnCode);
      return this;
    }

    public Builder returnMsg(String returnMsg) {
      apiResponse.setReturnMsg(returnMsg);
      return this;
    }

    public Builder appId(String appId) {
      apiResponse.setAppId(appId);
      return this;
    }

    public Builder tenantId(String tenantId) {
      apiResponse.setTenantId(tenantId);
      return this;
    }

    public Builder apiTransId(String apiTransId) {
      apiResponse.setApiTransId(apiTransId);
      return this;
    }

    public Builder timestamp(String timestamp) {
      apiResponse.setTimestamp(timestamp);
      return this;
    }

    public Builder nonceStr(String nonceStr) {
      apiResponse.setNonceStr(nonceStr);
      return this;
    }

    public Builder sign(String sign) {
      apiResponse.setSign(sign);
      return this;
    }

    public Builder signType(String signType) {
      apiResponse.setSignType(signType);
      return this;
    }

    public Builder resultCode(String resultCode) {
      apiResponse.setResultCode(resultCode);
      return this;
    }

    public Builder errCode(String errCode) {
      apiResponse.setErrCode(errCode);
      return this;
    }

    public Builder errCodeDes(String errCodeDes) {
      apiResponse.setErrCodeDes(errCodeDes);
      return this;
    }

    public ApiResponse build() {
      return apiResponse;
    }
  }
}
