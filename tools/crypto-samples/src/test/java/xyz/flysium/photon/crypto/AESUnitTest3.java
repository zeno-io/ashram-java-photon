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

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import xyz.flysium.photon.crypto.impl.AES;
import xyz.flysium.photon.crypto.impl.PBKDF2WithHmacSHA1;
import xyz.flysium.photon.crypto.support.CryptoSpiUnitTest;
import xyz.flysium.photon.crypto.support.CryptoUtil;
import xyz.flysium.photon.crypto.support.SpecUtil;

/**
 * AES Test.
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class AESUnitTest3 extends CryptoSpiUnitTest {

  private final String transforms = "AES/CBC/PKCS5Padding";
  private final String KEY = "17851d5650c868de";

  @Test
  public void testEncryptJsAndDecryptJava() throws Exception {
    String plainText = "17851d5650c868de";
    String jsCiperTextB64 = "TT2abjuKRJjmsnGK4SgJT3Oh+RNsyWmB5GOQ0x483t8=";
    testEncryptJsAndDecryptJava(aes, plainText, jsCiperTextB64);
  }

  private CryptoSpi aes;

  @Before
  public void before() {
    // aes = new AES();
    aes = new AES(transforms);
    //aes = new AES(new BouncyCastleProvider(), transforms);
    aesConfig(aes, KEY);
    LOGGER.info("-------密钥长度-------" + (aes.getSecret().length * 8));
  }

  private static SecretSpi pbkdf2() {
    return new PBKDF2WithHmacSHA1(128);
  }

  private static void aesConfig(CryptoSpi spi, String AESEncryptionKey) {
    // 约定送过来的key为hex编码形式，不小于32位长度部前面补0
    String saltHex = StringUtils.leftPad(AESEncryptionKey, 32, '0');
    String ivHex = StringUtils.leftPad(AESEncryptionKey, 32, '0');

    byte[] salt = CryptoUtil.unhex(saltHex);
    byte[] iv = CryptoUtil.unhex(ivHex);

    SecretSpi secretSpi = pbkdf2();
    secretSpi.setAlgorithmParameterSpec(SpecUtil.buildPBE(salt));
    String secretKeyHex = secretSpi.encryptStringHex(AESEncryptionKey);
    byte[] secretKey = CryptoUtil.unhex(secretKeyHex);

    spi.setSecret(secretKey);
    spi.setAlgorithmParameterSpec(SpecUtil.buildIV(iv));
  }


}
