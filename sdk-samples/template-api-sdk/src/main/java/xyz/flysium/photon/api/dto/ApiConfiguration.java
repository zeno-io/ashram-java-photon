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
