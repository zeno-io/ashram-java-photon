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

package xyz.flysium.photon.api.proxy;

import xyz.flysium.photon.api.ExtendReverseApiRequest;
import xyz.flysium.photon.api.ReverseApiRequest;

/**
 * API通知上下文
 *
 * @author Sven Augustus
 */
@SuppressWarnings("PMD.UnusedPrivateField")
class ApiReverseContext<I extends ExtendReverseApiRequest> {

  /* 通知对象 */
  private ReverseApiRequest notifyApiRequest;

  /* 扩展通知对象, 一般为业务扩展 */
  private I request;

  /* 通知URL */
  private String url;

  /* 安全密钥 */
  private String apiKey;

  public ApiReverseContext() {
  }

  public ReverseApiRequest getNotifyApiRequest() {
    return notifyApiRequest;
  }

  public I getRequest() {
    return request;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public static <I extends ExtendReverseApiRequest> Builder<I> builder() {
    return new Builder<I>();
  }

  public static final class Builder<I extends ExtendReverseApiRequest> {

    private final ApiReverseContext<I> apiReverseContext;

    private Builder() {
      apiReverseContext = new ApiReverseContext<I>();
    }

    public Builder<I> notifyApiRequest(ReverseApiRequest notifyApiRequest) {
      apiReverseContext.notifyApiRequest = notifyApiRequest;
      return this;
    }

    public Builder<I> request(I request) {
      apiReverseContext.request = request;
      return this;
    }

    public Builder<I> url(String url) {
      apiReverseContext.setUrl(url);
      return this;
    }

    public Builder<I> apiKey(String apiKey) {
      apiReverseContext.setApiKey(apiKey);
      return this;
    }

    public ApiReverseContext<I> build() {
      return apiReverseContext;
    }
  }
}
