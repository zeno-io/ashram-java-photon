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

package xyz.flysium.photon.crypto;

import java.security.Provider;

/**
 * Asymmetric encryption and decryption SPI
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
abstract class AbstractAsyCryptoSpi extends AbstractCryptoSpi implements AsyCryptoSpi {

  AbstractAsyCryptoSpi(String algorithm, Provider provider, String transforms) {
    super(algorithm, provider, transforms);
  }

}
