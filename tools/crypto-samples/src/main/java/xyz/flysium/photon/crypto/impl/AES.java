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
import javax.crypto.spec.SecretKeySpec;
import xyz.flysium.photon.crypto.AbstractSymmetric;
import xyz.flysium.photon.crypto.CryptoSpi;
import xyz.flysium.photon.crypto.SecretSpi;


/**
 * AES
 *
 * @author Sven  MMMM
 * @version 1.0
 * @since JDK 1.7
 */
public class AES extends AbstractSymmetric implements CryptoSpi, SecretSpi {

  /**
   * Advanced Encryption Standard as specified by NIST in FIPS 197.  Also known as the Rijndael
   * algorithm by Joan Daemen and Vincent Rijmen,  AES is a 128-bit block cipher supporting keys of
   * 128, 192, and 256 bits.
   */
  private static final String DEFAULT_ALGORITHM = "AES";
  /**
   * default transforms
   */
  private static final String DEFAULT_TRANSFORMS = "AES";
  /**
   * default key length，256 bits
   */
  public static final int DEFAULT_KEY_SIZE = 256;
  private static final int ALLOW_KEY_SIZE_128 = 128;
  private static final int ALLOW_KEY_SIZE_192 = 192;
  private static final int ALLOW_KEY_SIZE_256 = 256;

  public AES() {
    this(null, DEFAULT_TRANSFORMS);
  }

  public AES(Provider provider) {
    this(provider, DEFAULT_TRANSFORMS);
  }

  public AES(String transforms) {
    this(null, transforms);
  }

  public AES(Provider provider, String transforms) {
    super(DEFAULT_ALGORITHM, provider, transforms);
  }

  @Override
  public void setSecret(byte[] secretKey) {
    if (secretKey == null) {
      return;
    }
    // 使用SecretKeySpec构建
    this.secretKey = new SecretKeySpec(secretKey, algorithm);
  }

  /**
   * Generate a random key
   */
  @Override
  public void generateKey() {
    generateKey(DEFAULT_KEY_SIZE);
  }

  @Override
  public void generateKey(int keyLength) {
    if (keyLength != ALLOW_KEY_SIZE_128 && keyLength != ALLOW_KEY_SIZE_192
      && keyLength != ALLOW_KEY_SIZE_256) {
      throw new IllegalStateException("Invalid key size (" + keyLength + " bits)");
    }
    super.generateKey(keyLength);
  }

}
