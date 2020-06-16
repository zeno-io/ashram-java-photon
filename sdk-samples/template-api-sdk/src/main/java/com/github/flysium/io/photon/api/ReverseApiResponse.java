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
 * 通知返回对象
 *
 * @author Sven Augustus
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public class ReverseApiResponse implements java.io.Serializable {

  private static final long serialVersionUID = 4655297366604866598L;

  private String returnCode;

  private String returnMsg;

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

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private final ReverseApiResponse reverseApiResponse;

    private Builder() {
      reverseApiResponse = new ReverseApiResponse();
    }

    public Builder returnCode(String returnCode) {
      reverseApiResponse.setReturnCode(returnCode);
      return this;
    }

    public Builder returnMsg(String returnMsg) {
      reverseApiResponse.setReturnMsg(returnMsg);
      return this;
    }

    public ReverseApiResponse build() {
      return reverseApiResponse;
    }
  }
}
