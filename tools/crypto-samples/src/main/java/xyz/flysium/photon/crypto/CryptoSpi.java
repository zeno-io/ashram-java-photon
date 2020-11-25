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

import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * Encryption and decryption SPI (reversible SPI)
 *
 * <p>
 * JCE (Java Cryptographic Extension): Enhances JCA functionality with cryptographic services that
 * follow US export control regulations, supports encryption and decryption operations, supports key
 * generation and negotiation, and supports Message Authentication Code.
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public interface CryptoSpi extends SecretSpi {

  /**
   * Get the transforms name
   *
   * @return the transforms name
   */
  String getTransforms();

  /**
   * Get the secret
   *
   * @return the secret
   */
  byte[] getSecret();

  /**
   * Set the secret
   *
   * @param secretKey the secret
   */
  void setSecret(byte[] secretKey);

  /**
   * Randomly generate a key based on the default key length
   */
  void generateKey();

  /**
   * Randomly generate a key based on the initial key length
   *
   * @param keyLength the initial key length (in Bits)
   */
  void generateKey(int keyLength);

  /**
   * decrypt
   *
   * @param cipherText cipher text data
   * @return plain data
   */
  byte[] decrypt(byte[] cipherText);

  /**
   * decrypt string
   *
   * @param cipherText cipher text data
   * @return plain data
   */
  default String decryptString(String cipherText) {
    return decryptString(cipherText, CryptoUtil.DEFAULT_CHARSET.name());
  }


  /**
   * decrypt the base64 string
   *
   * @param cipherTextB64 cipher text in Base64 encoded form
   * @return plain data
   */
  default String decryptStringB64(String cipherTextB64) {
    return decryptStringB64(cipherTextB64, CryptoUtil.DEFAULT_CHARSET.name());
  }

  /**
   * decrypt the Hex string
   *
   * @param cipherTextHex cipher text in Hex encoded form
   * @return plain data
   */
  default String decryptStringHex(String cipherTextHex) {
    return decryptStringHex(cipherTextHex, CryptoUtil.DEFAULT_CHARSET.name());
  }

  /**
   * decrypt string
   *
   * @param cipherText  cipher text data
   * @param charsetName charset name
   * @return plain data
   */
  String decryptString(String cipherText, String charsetName);

  /**
   * decrypt the base64 string
   *
   * @param cipherTextB64 cipher text in Base64 encoded form
   * @param charsetName   charset name
   * @return plain data
   */
  String decryptStringB64(String cipherTextB64, String charsetName);

  /**
   * decrypt the Hex string
   *
   * @param cipherTextHex cipher text in Hex encoded form
   * @param charsetName   charset name
   * @return plain data
   */
  String decryptStringHex(String cipherTextHex, String charsetName);

}
