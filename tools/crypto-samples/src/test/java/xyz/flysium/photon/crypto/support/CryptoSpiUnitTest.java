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

package xyz.flysium.photon.crypto.support;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.crypto.CryptoSpi;


/**
 * Unit Test
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class CryptoSpiUnitTest {

  protected static final Logger LOGGER = LoggerFactory.getLogger(CryptoSpiUnitTest.class);

  protected void testEncryptAndDecrypt(CryptoSpi spi, String plainText) {
    LOGGER.info("-------Java加密前明文-------" + plainText);
    String ciperTextB64 = spi.encryptStringB64(plainText);
    LOGGER.info("-------Java加密后密文-------" + ciperTextB64);
    String decryptedText = spi.decryptStringB64(ciperTextB64);
    LOGGER.info("-------Java解密后明文-------" + decryptedText);
    Assert.assertEquals(plainText, decryptedText);
  }

  protected void testEncryptJsAndDecryptJava(CryptoSpi spi, String plainText,
    String jsCiperTextB64)
    throws Exception {
    LOGGER.info("-------JavaScript加密前明文-------" + plainText);
    LOGGER.info("-------JavaScript加密后密文-------" + jsCiperTextB64);
    String decryptedText = spi.decryptStringB64(jsCiperTextB64);
    LOGGER.info("-------Java解密后明文-------" + decryptedText);
    Assert.assertEquals(plainText, decryptedText);
  }


}
