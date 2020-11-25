/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.photon.crypto.impl;

import java.security.Provider;
import xyz.flysium.photon.crypto.Digest;
import xyz.flysium.photon.crypto.SecretSpi;

/**
 * SHA256
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class SHA256 extends Digest implements SecretSpi {

  /**
   * default algorithm
   */
  private static final String DEFAULT_ALGORITHM = "SHA-256";

  public SHA256() {
    super(DEFAULT_ALGORITHM, null);
  }

  public SHA256(Provider provider) {
    super(DEFAULT_ALGORITHM, provider);
  }

}
