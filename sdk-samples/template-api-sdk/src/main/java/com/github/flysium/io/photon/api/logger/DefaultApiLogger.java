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

package com.github.flysium.io.photon.api.logger;

import com.github.flysium.io.photon.api.ApiLogger;
import com.github.flysium.io.photon.api.dto.ApiLogVO;
import lombok.AllArgsConstructor;

/**
 * APIProperties 日志记录
 *
 * @author Sven Augustus
 */
@AllArgsConstructor
public class DefaultApiLogger implements ApiLogger {

  @Override
  public void logger(ApiLogVO log, Object response) {
    // TODO 异步写日志
  }

}
