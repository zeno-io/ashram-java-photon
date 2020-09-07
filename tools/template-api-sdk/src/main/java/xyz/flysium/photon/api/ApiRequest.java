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
