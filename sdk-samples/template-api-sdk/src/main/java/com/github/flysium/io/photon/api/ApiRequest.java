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
 * 请求对象
 *
 * @author Sven Augustus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

}
