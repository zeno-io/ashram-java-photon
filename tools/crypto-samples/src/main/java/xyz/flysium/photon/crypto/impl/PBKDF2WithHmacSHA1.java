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
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import xyz.flysium.photon.crypto.AbstractPBESpec;
import xyz.flysium.photon.crypto.SecretSpi;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * PBKDF2
 *
 * <p>
 * Constructs secret keys using the Password-Based Key Derivation Function function found in PKCS #5
 * v2.0.
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class PBKDF2WithHmacSHA1 extends AbstractPBESpec implements SecretSpi {

  /**
   * default algorithm
   */
  private static final String DEFAULT_ALGORITHM = "PBKDF2WithHmacSHA1";

  private int keyLength = 128;

  public PBKDF2WithHmacSHA1() {
    super(DEFAULT_ALGORITHM, null);
  }

  public PBKDF2WithHmacSHA1(Provider provider) {
    super(DEFAULT_ALGORITHM, provider);
  }

  public PBKDF2WithHmacSHA1(int keyLength) {
    this();
    this.keyLength = keyLength;
  }

  @Override
  public byte[] encrypt(char[] plainText) {
    AlgorithmParameterSpec parameterSpec = this.getAlgorithmParameterSpec();
    if (!(parameterSpec instanceof PBEParameterSpec)) {
      fail(new Exception("Invalid AlgorithmParameterSpec!"));
      return new byte[0];
    }
    PBEParameterSpec pbeParameterSpec = (PBEParameterSpec) parameterSpec;
    byte[] salt = pbeParameterSpec.getSalt();
    int iterationCount = pbeParameterSpec.getIterationCount();

    SecretKeyFactory factory = CryptoUtil.getSecretKeyFactory(algorithm, provider);
    try {
      if (factory != null) {
        KeySpec spec = new PBEKeySpec(plainText, salt, iterationCount, this.keyLength);
        return factory.generateSecret(spec).getEncoded();
      }
    } catch (InvalidKeySpecException e) {
      fail(e);
    }
    return new byte[0];
  }

}
