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

package xyz.flysium.photon.api.dto;

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
