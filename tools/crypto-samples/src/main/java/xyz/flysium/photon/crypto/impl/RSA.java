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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import org.apache.commons.lang3.StringUtils;
import xyz.flysium.photon.crypto.AbstractAsymmetric;
import xyz.flysium.photon.crypto.AsyCryptoSpi;
import xyz.flysium.photon.crypto.SecretSpi;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * RSA
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class RSA extends AbstractAsymmetric implements AsyCryptoSpi, SecretSpi {

  /**
   * The RSA encryption algorithm as defined in PKCS #1
   */
  private static final String DEFAULT_ALGORITHM = "RSA";
  /**
   * default transforms
   */
  private static final String DEFAULT_TRANSFORMS = "RSA";
  /**
   * default key length，1024 bits
   */
  public static final int DEFAULT_KEY_SIZE = 1024;
  /**
   * default signature algorithm
   */
  private static final String DEFAULT_SIGNATURE_ALGORITHM = "SHA256withRSA";


  public RSA() {
    this(NULL_PROVIDER, DEFAULT_TRANSFORMS);
  }

  public RSA(Provider provider) {
    this(provider, DEFAULT_TRANSFORMS);
  }

  public RSA(String transforms) {
    this(NULL_PROVIDER, transforms);
  }

  public RSA(Provider provider, String transforms) {
    super(DEFAULT_ALGORITHM, provider, transforms);
    super.setSignatureAlgorithm(DEFAULT_SIGNATURE_ALGORITHM);
  }

  public RSA(String publicKeyStr, String privateKeyStr) {
    this(null, DEFAULT_TRANSFORMS, publicKeyStr, privateKeyStr);
  }

  public RSA(Provider provider, String transforms, String publicKeyB64, String privateKeyB64) {
    super(DEFAULT_ALGORITHM, provider, transforms, publicKeyB64, privateKeyB64);
    super.setSignatureAlgorithm(DEFAULT_SIGNATURE_ALGORITHM);
  }

  /**
   * Set public key
   *
   * @param publicKeyB64 Base64 encoded public key text
   */
  @Override
  public void setPublicKey(String publicKeyB64) {
    if (StringUtils.isEmpty(publicKeyB64)) {
      return;
    }
    try {
      byte[] buffer = CryptoUtil.unarmor(publicKeyB64);
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);

      KeyFactory keyFactory = CryptoUtil.getKeyFactory(algorithm, provider);
      this.publicKey = keyFactory.generatePublic(keySpec);
    } catch (InvalidKeySpecException e) {
      fail(e);
    }
  }

  /**
   * Set the private key
   *
   * @param privateKeyB64 Base64 encoded private key text
   */
  @Override
  public void setPrivateKey(String privateKeyB64) {
    if (StringUtils.isEmpty(privateKeyB64)) {
      return;
    }
    try {
      byte[] buffer = CryptoUtil.unarmor(privateKeyB64);
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);

      KeyFactory keyFactory = CryptoUtil.getKeyFactory(algorithm, provider);
      this.privateKey = keyFactory.generatePrivate(keySpec);
    } catch (InvalidKeySpecException e) {
      fail(e);
    }
  }

  /**
   * Generate a random key
   */
  @Override
  public void generateKey() {
    generateKey(DEFAULT_KEY_SIZE);
  }

  /**
   * Encryption or decryption operations
   *
   * @param cipher Cipher object
   * @param mode   mode
   * @param input  Input buffer
   * @return a new buffer containing the result
   */
  @Override
  protected byte[] doFinal(Cipher cipher, int mode, byte[] input) {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      // java.lang.ArrayIndexOutOfBoundsException: too much data for RSA block
      // Padding模式下，其中PKCS#1建议的Padding就占用了11个字节
      // In Padding mode, Padding, which is recommended by PKCS#1, occupies 11 bytes.
      final int keyLength = ((RSAPublicKey) publicKey).getModulus().bitLength();
      final int defaultBlockLength =
        (Cipher.DECRYPT_MODE == mode) ? (keyLength / 8) : (keyLength / 8 - 11);
      final int maxBlockLength =
        (cipher.getBlockSize() > 0) ? cipher.getBlockSize() : defaultBlockLength;
      int length = input.length;
      if (length <= maxBlockLength) {
        return cipher.doFinal(input);
      }

      int offSet = 0;
      byte[] cache;
      int i = 0;
      while (length - offSet > 0) {
        if (length - offSet <= maxBlockLength) {
          cache = cipher.doFinal(input, offSet, length - offSet);
        } else {
          cache = cipher.doFinal(input, offSet, maxBlockLength);
        }
        out.write(cache, 0, cache.length);
        i++;
        offSet = i * maxBlockLength;
      }
      return out.toByteArray();

    } catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
      fail(e);
    }
    return new byte[0];
  }

}
