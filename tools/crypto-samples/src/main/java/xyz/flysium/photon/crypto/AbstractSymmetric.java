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

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * Symmetric encryption and decryption algorithm
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public abstract class AbstractSymmetric extends AbstractCryptoSpi {

  /**
   * Symmetric secret
   */
  protected SecretKey secretKey = null;
  private static final int IV_LENGTH = 16;

  public AbstractSymmetric(String algorithm, Provider provider, String transforms) {
    super(algorithm, provider, transforms);
  }

  @Override
  public byte[] getSecret() {
    return secretKey.getEncoded();
  }

  @Override
  public void setAlgorithmParameterSpec(AlgorithmParameterSpec spec) {
    if (spec == null) {
      return;
    }
    if (spec instanceof IvParameterSpec) {
      byte[] iv = ((IvParameterSpec) spec).getIV();
      if (iv == null || iv.length != IV_LENGTH) {
        throw new IllegalStateException(
          "Invalid iv size (" + (iv == null ? 0 : iv.length) + " bits)");
      }
    }
    super.setAlgorithmParameterSpec(spec);
  }

  @Override
  public void generateKey(int keyLength) {
    this.secretKey = CryptoUtil.generateKey(keyLength, algorithm, provider);
  }

  @Override
  public byte[] encrypt(byte[] plainText) {
    Cipher cipher = getCipher();

    try {
      if (cipher != null) {
        if (algorithmParameterSpec != null) {
          cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, algorithmParameterSpec);
        } else {
          cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        }

        return doFinal(cipher, Cipher.ENCRYPT_MODE, plainText);
      }
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      fail(e);
    }
    return new byte[0];
  }

  @Override
  public byte[] decrypt(byte[] cipherText) {
    Cipher cipher = getCipher();
    try {
      if (cipher != null) {
        if (algorithmParameterSpec != null) {
          cipher.init(Cipher.DECRYPT_MODE, this.secretKey, algorithmParameterSpec);
        } else {
          cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        }

        return doFinal(cipher, Cipher.DECRYPT_MODE, cipherText);
      }
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      fail(e);
    }
    return new byte[0];
  }

  protected byte[] doFinal(Cipher cipher, int mode, byte[] input) {
    try {
      return cipher.doFinal(input);
    } catch (IllegalBlockSizeException | BadPaddingException e) {
      fail(e);
    }
    return new byte[0];
  }

}
