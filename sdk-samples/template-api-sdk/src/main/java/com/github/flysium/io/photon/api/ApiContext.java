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
 * APIProperties 协议相关上下文
 *
 * @author Sven Augustus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateField")
public class ApiContext {

  private transient String protocolType;

  private transient String clientIp;

  private transient String serverIp;

  private String url;

  private static ThreadLocal<ApiContext> contextThreadLocal = new ThreadLocal<ApiContext>() {
    @Override
    protected ApiContext initialValue() {
      return new ApiContext();
    }
  };

  public static ApiContext get() {
    return contextThreadLocal.get();
  }

  public static void set(ApiContext value) {
    contextThreadLocal.set(value);
  }

  public static void remove() {
    contextThreadLocal.remove();
  }
}
