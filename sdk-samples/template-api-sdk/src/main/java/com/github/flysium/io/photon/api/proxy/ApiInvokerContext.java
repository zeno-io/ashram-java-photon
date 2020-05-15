/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.api.proxy;

import com.github.flysium.io.photon.api.ApiRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * API业务调用请求上下文
 *
 * @author Sven Augustus
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateField")
class ApiInvokerContext<I extends ApiRequest> {

  /* 请求对象 */
  @Getter
  private I request;

  /* 是否安全认证 */
  @Getter
  private boolean signatureCheckEnabled;

  /* 安全密钥 */
  @Getter
  @Setter
  private String apiKey;
}
