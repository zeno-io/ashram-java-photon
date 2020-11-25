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
import javax.crypto.Cipher;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * Encryption and decryption SPI (reversible SPI)
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
abstract class AbstractCryptoSpi extends AbstractSecretSpi implements CryptoSpi {

  /**
   * 转换的名称，“算法名/算法模式/填充模式”（Cipher Algorithm Names/Modes/Padding），例如 AES/ECB/PKCS5Padding。
   * 有关标准转换名称的信息，请参见 Java Cryptography Architecture Reference Guide 的附录A：
   * <a>https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher</a>
   */
  protected final String transforms;
  private final Cipher cipher;

  protected Cipher getCipher() {
    return cipher;
  }

  public AbstractCryptoSpi(String algorithm, Provider provider, String transforms) {
    super(algorithm, provider);
    this.transforms = transforms;
    this.cipher = CryptoUtil
      .getCipher(AbstractCryptoSpi.this.transforms, AbstractCryptoSpi.this.provider);
  }

  @Override
  public String getTransforms() {
    return transforms;
  }

  @Override
  public String decryptString(String cipherText, String charsetName) {
    try {
      byte[] decryptedData = decrypt(cipherText.getBytes(charsetName));
      return new String(decryptedData, charsetName);
    } catch (UnsupportedEncodingException e) {
      fail(e);
    }
    return null;
  }

  @Override
  public String decryptStringB64(String cipherTextB64, String charsetName) {
    try {
      byte[] decryptedData = decrypt(CryptoUtil.unarmor(cipherTextB64, charsetName));
      return new String(decryptedData, charsetName);
    } catch (UnsupportedEncodingException e) {
      fail(e);
    }
    return null;
  }

  @Override
  public String decryptStringHex(String cipherTextHex, String charsetName) {
    try {
      byte[] decryptedData = decrypt(CryptoUtil.unhex(cipherTextHex));
      return new String(decryptedData, charsetName);
    } catch (UnsupportedEncodingException e) {
      fail(e);
    }
    return null;
  }

}
