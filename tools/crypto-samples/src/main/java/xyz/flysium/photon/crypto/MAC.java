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

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.Provider;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * MAC
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class MAC extends AbstractSecretSpi implements MacSpi, SecretSpi {

  /**
   * default algorithm
   */
  private static final String DEFAULT_ALGORITHM = "HmacSHA256";

  private Key key;

  public MAC() {
    super(DEFAULT_ALGORITHM, null);
  }

  public MAC(Provider provider) {
    super(DEFAULT_ALGORITHM, provider);
  }

  @Override
  public Key getKey() {
    return key;
  }

  @Override
  public void setKey(Key key) {
    this.key = key;
  }

  @Override
  public void setKey(String key) {
    this.key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
  }

  @Override
  public byte[] encrypt(byte[] plainText) {
    Mac mac = CryptoUtil.getMac(algorithm, provider);
    try {
      if (mac != null) {
        mac.init(key);
        return mac.doFinal(plainText);
      }
    } catch (InvalidKeyException e) {
      fail(e);
    }
    return new byte[0];
  }

}
