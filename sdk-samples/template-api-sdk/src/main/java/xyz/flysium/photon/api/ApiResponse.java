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

package xyz.flysium.photon.api;

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
