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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应对象
 *
 * @author Sven Augustus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

}
