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

import java.security.MessageDigest;
import java.security.Provider;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * Information digest, irreversible encryption algorithm
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class Digest extends AbstractSecretSpi {

  private final MessageDigest messageDigest;

  public Digest(String algorithm, Provider provider) {
    super(algorithm, provider);
    this.messageDigest = CryptoUtil
      .getMessageDigest(Digest.this.algorithm, Digest.this.provider);
  }

  @Override
  public byte[] encrypt(byte[] plainText) {
    if (this.messageDigest != null) {
      this.messageDigest.update(plainText);
      return this.messageDigest.digest();
    }
    return new byte[0];
  }

}
