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

package xyz.flysium.photon.api.service;

import xyz.flysium.photon.api.dto.ApiConfiguration;
import xyz.flysium.photon.api.dto.AppSecret;

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
