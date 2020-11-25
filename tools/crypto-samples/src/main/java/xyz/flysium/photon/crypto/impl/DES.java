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

import java.security.InvalidKeyException;
import java.security.Provider;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import xyz.flysium.photon.crypto.AbstractSymmetric;
import xyz.flysium.photon.crypto.CryptoSpi;
import xyz.flysium.photon.crypto.SecretSpi;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * DES
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class DES extends AbstractSymmetric implements CryptoSpi, SecretSpi {

  /**
   * default algorithm
   */
  private static final String DEFAULT_ALGORITHM = "DES";
  /**
   * default transforms
   */
  private static final String DEFAULT_TRANSFORMS = "DES";
  /**
   * default key length，56 bits
   */
  public static final int DEFAULT_KEY_SIZE = 56;
  private static final int ALLOW_KEY_SIZE_56 = 56;

  public DES() {
    this(null, DEFAULT_TRANSFORMS);
  }

  public DES(Provider provider) {
    this(provider, DEFAULT_TRANSFORMS);
  }

  public DES(String transforms) {
    this(null, transforms);
  }

  public DES(Provider provider, String transforms) {
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
    if (keyLength != ALLOW_KEY_SIZE_56) {
      throw new IllegalStateException("Invalid key size (" + keyLength + " bits)");
    }
    super.generateKey(keyLength);
  }

  @Override
  public void setSecret(byte[] secretKey) {
    if (secretKey == null) {
      return;
    }
    try {
      // DES key padding must be 8 B
      byte[] key = new byte[8];
      if (key.length > secretKey.length) {
        System.arraycopy(secretKey, 0, key, 0, secretKey.length);
      } else {
        System.arraycopy(secretKey, 0, key, 0, key.length);
      }
      DESKeySpec secretKeySpec = new DESKeySpec(key);
      SecretKeyFactory keyFactory = CryptoUtil.getSecretKeyFactory(algorithm, provider);
      this.secretKey = keyFactory.generateSecret(secretKeySpec);
    } catch (InvalidKeyException | InvalidKeySpecException e) {
      fail(e);
    }
    // DES key length is 56 Bits, and another 8 bits are parity bits.
  }

}
