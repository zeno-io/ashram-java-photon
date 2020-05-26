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

import com.github.flysium.io.photon.api.dto.ApiLogVO;
import com.github.flysium.io.photon.api.logger.DefaultApiLogger;

/**
 * APIProperties 日志记录器
 *
 * @author Sven Augustus
 */
public interface ApiLogger {

  /**
   * APIProperties 日志记录
   *
   * @param log 日志对象
   */
  default void logger(ApiLogVO log) {
    logger(log, null);
  }

  /**
   * APIProperties 日志记录
   *
   * @param log 日志对象
   * @param response 响应对象
   */
  void logger(ApiLogVO log, Object response);

  /**
   * 获取API 日志记录器
   *
   * @return 日志记录器
   */
  static ApiLogger getApiLogger() {
    // TODO
    return new DefaultApiLogger();
  }

}
