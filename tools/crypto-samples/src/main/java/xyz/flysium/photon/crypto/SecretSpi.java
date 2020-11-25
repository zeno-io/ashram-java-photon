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
import java.security.spec.AlgorithmParameterSpec;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * Encrypted SPI
 *
 * <p>Corresponding to Java encryption architecture（Java Cryptograp Architecture）：
 * <p>Provides basic cryptographic services and encryption algorithms, including support for
 * digital signatures and message digests.
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public interface SecretSpi {

  /**
   * Get the algorithm name
   *
   * @return the algorithm name
   */
  String getAlgorithm();

  /**
   * Get the algorithm provider
   *
   * @return the algorithm provider
   */
  Provider getProvider();

  /**
   * Get the encryption parameter specification object
   *
   * @return the encryption parameter specification object
   */
  AlgorithmParameterSpec getAlgorithmParameterSpec();

  /**
   * set the encryption parameter specification object
   *
   * @param algorithmParameterSpec the encryption parameter specification object
   */
  void setAlgorithmParameterSpec(AlgorithmParameterSpec algorithmParameterSpec);

  /**
   * encrypt
   *
   * @param plainText plain data
   * @return cipher text data
   */
  byte[] encrypt(byte[] plainText);

  /**
   * encrypt
   *
   * @param plainText plain data
   * @return cipher text data
   */
  byte[] encrypt(char[] plainText);

  /**
   * encrypt string
   *
   * @param plainText plain data
   * @return cipher text data
   */
  default String encryptString(String plainText) {
    return encryptString(plainText, CryptoUtil.DEFAULT_CHARSET.name());
  }

  /**
   * encrypt string to base64 string
   *
   * @param plainText plain data
   * @return cipher text in Base64 encoded form
   */
  default String encryptStringB64(String plainText) {
    return encryptStringB64(plainText, CryptoUtil.DEFAULT_CHARSET.name());
  }

  /**
   * encrypt string to hex string
   *
   * @param plainText plain data
   * @return cipher text in Hex encoded form
   */
  default String encryptStringHex(String plainText) {
    return encryptStringHex(plainText, CryptoUtil.DEFAULT_CHARSET.name());
  }

  /**
   * encrypt string
   *
   * @param plainText   plain data
   * @param charsetName charset name
   * @return cipher text data
   */
  String encryptString(String plainText, String charsetName);

  /**
   * encrypt string to base64 string
   *
   * @param plainText   plain data
   * @param charsetName charset name
   * @return cipher text in Base64 encoded form
   */
  String encryptStringB64(String plainText, String charsetName);

  /**
   * encrypt string to hex string
   *
   * @param plainText   plain data
   * @param charsetName charset name
   * @return cipher text in Hex encoded form
   */
  String encryptStringHex(String plainText, String charsetName);


}
