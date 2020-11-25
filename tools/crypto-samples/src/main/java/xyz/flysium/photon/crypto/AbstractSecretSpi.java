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

import java.io.UnsupportedEncodingException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * 加密SPI
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public abstract class AbstractSecretSpi implements SecretSpi {

  /**
   * the algorithm name
   */
  protected final String algorithm;
  /**
   * the provider of the CipherSpi implementation
   */
  protected final Provider provider;
  /**
   * Empty provider of the CipherSpi implementation
   */
  protected static final Provider NULL_PROVIDER = null;
  /**
   * the encryption parameter specification object
   */
  protected AlgorithmParameterSpec algorithmParameterSpec;
  /**
   * is it Support byte[] encryption ?
   */
  private final boolean byteEncryptSupport;
  /**
   * is it Support char[] encryption ?
   */
  private final boolean charEncryptSupport;


  public AbstractSecretSpi(String algorithm, Provider provider) {
    this(algorithm, provider, true, false);
  }

  public AbstractSecretSpi(String algorithm, Provider provider, boolean byteEncryptSupport,
    boolean charEncryptSupport) {
    super();
    this.algorithm = algorithm;
    this.provider = provider;
    this.byteEncryptSupport = byteEncryptSupport;
    this.charEncryptSupport = charEncryptSupport;
  }

  @Override
  public String getAlgorithm() {
    return algorithm;
  }

  @Override
  public Provider getProvider() {
    return provider;
  }

  @Override
  public AlgorithmParameterSpec getAlgorithmParameterSpec() {
    return algorithmParameterSpec;
  }

  @Override
  public void setAlgorithmParameterSpec(AlgorithmParameterSpec algorithmParameterSpec) {
    this.algorithmParameterSpec = algorithmParameterSpec;
  }

  @Override
  public byte[] encrypt(byte[] plainText) {
    if (!byteEncryptSupport) {
      throw new UnsupportedOperationException("not support to encrypt byte[] in SecretSpi.");
    }
    return new byte[0];
  }

  @Override
  public byte[] encrypt(char[] plainText) {
    if (!charEncryptSupport) {
      throw new UnsupportedOperationException("not support to encrypt char[] in SecretSpi.");
    }
    return new byte[0];
  }

  public byte[] encryptString2Bytes(String plainText, String charsetName) {
    try {
      byte[] encryptedData;
      if (charEncryptSupport) {
        encryptedData = encrypt(plainText.toCharArray());
      } else {
        encryptedData = encrypt(plainText.getBytes(charsetName));
      }
      return encryptedData;
    } catch (UnsupportedEncodingException e) {
      fail(e);
    }
    return new byte[0];
  }

  @Override
  public String encryptString(String plainText, String charsetName) {
    try {
      byte[] encryptedData = encryptString2Bytes(plainText, charsetName);
      return new String(encryptedData, charsetName);
    } catch (UnsupportedEncodingException e) {
      fail(e);
    }
    return null;
  }

  @Override
  public String encryptStringB64(String plainText, String charsetName) {
    try {
      byte[] encryptedData = encryptString2Bytes(plainText, charsetName);
      return new String(CryptoUtil.armor(encryptedData), charsetName);
    } catch (UnsupportedEncodingException e) {
      fail(e);
    }
    return null;
  }

  @Override
  public String encryptStringHex(String plainText, String charsetName) {
    byte[] encryptedData = encryptString2Bytes(plainText, charsetName);
    return CryptoUtil.hex(encryptedData);
  }

  /**
   * rethrow exception
   *
   * @param e exception
   */
  protected void fail(Exception e) {
    throw new IllegalStateException(e);
  }

}
