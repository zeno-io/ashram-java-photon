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


/**
 * Asymmetric encryption and decryption SPI
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public interface AsyCryptoSpi extends CryptoSpi, SecretSpi {

  /**
   * Get public key text (Base64)
   *
   * @return Base64 encoded public key text
   */
  String getPublicKey();

  /**
   * Get private key text (Base64)
   *
   * @return Base64 encoded private key text
   */
  String getPrivateKey();

  /**
   * Set public key text (Base64)
   *
   * @param publicKeyB64 Base64 encoded public key text
   */
  void setPublicKey(String publicKeyB64);

  /**
   * Set private key text (Base64)
   *
   * @param privateKeyB64 Base64 encoded private key text
   */
  void setPrivateKey(String privateKeyB64);

  /**
   * Get signature algorithm
   *
   * @return the signature algorithm name
   */
  String getSignatureAlgorithm();

  /**
   * Set signature algorithm
   *
   * @param signatureAlgorithm the signature algorithm name
   */
  void setSignatureAlgorithm(String signatureAlgorithm);

  /**
   * signature
   *
   * @param input Input buffer
   * @return Signature byte of the result of the signature operation
   */
  byte[] sign(byte[] input);

  /**
   * verify the signature
   *
   * @param input Input buffer
   * @param sign  the signature
   * @return Returns true if the signature is validated, otherwise returns false.
   */
  boolean verify(byte[] input, byte[] sign);

}
