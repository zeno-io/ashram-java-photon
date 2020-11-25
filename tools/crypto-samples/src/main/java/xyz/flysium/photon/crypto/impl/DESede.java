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
 * DESede(the 3DES)
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class DESede extends AbstractSymmetric implements CryptoSpi, SecretSpi {

  /**
   * default algorithm
   */
  private static final String DEFAULT_ALGORITHM = "DESede";
  /**
   * default transforms
   */
  private static final String DEFAULT_TRANSFORMS = "DESede";
  /**
   * default key length，168 bits
   */
  public static final int DEFAULT_KEY_SIZE = 168;
  private static final int ALLOW_KEY_SIZE_MIN = 112;
  private static final int ALLOW_KEY_SIZE_MAX = 168;

  public DESede() {
    this(null, DEFAULT_TRANSFORMS);
  }

  public DESede(Provider provider) {
    this(provider, DEFAULT_TRANSFORMS);
  }

  public DESede(String transforms) {
    this(null, transforms);
  }

  public DESede(Provider provider, String transforms) {
    super(DEFAULT_ALGORITHM, provider, transforms);
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
    if (keyLength < ALLOW_KEY_SIZE_MIN || keyLength > ALLOW_KEY_SIZE_MAX) {
      throw new IllegalStateException("Invalid key size (" + keyLength + " bits)");
    }
    super.generateKey(keyLength);
  }

  @Override
  public void setSecret(byte[] secretKey) {
    if (secretKey == null) {
      return;
    }
    // 3DES key padding must be 24 B
    byte[] key = new byte[24];
    if (key.length > secretKey.length) {
      System.arraycopy(secretKey, 0, key, 0, secretKey.length);
    } else {
      System.arraycopy(secretKey, 0, key, 0, key.length);
    }
    this.secretKey = new SecretKeySpec(key, algorithm);
  }

}
