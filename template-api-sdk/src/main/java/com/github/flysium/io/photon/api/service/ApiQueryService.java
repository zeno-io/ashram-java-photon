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

package com.github.flysium.io.photon.api.service;

import com.github.flysium.io.photon.api.dto.ApiConfiguration;
import com.github.flysium.io.photon.api.dto.AppSecret;

/**
 * APIProperties 查询器
 *
 * @author Sven Augustus
 */
public interface ApiQueryService {

  /**
   * 根据应用ID查询应用密钥
   *
   * @param appId 应用ID
   * @return 应用密钥，如果不存在返回 null
   */
  AppSecret queryAppSecretByAppId(String appId);

  /**
   * 根据应用ID、API编码查询API配置
   *
   * @param appId 应用ID
   * @param apiId API编码
   * @return API配置，如果不存在返回 null
   */
  ApiConfiguration queryApiConfigurationByAppId(String appId, String apiId);

}
