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
