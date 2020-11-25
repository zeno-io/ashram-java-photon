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
import xyz.flysium.photon.crypto.AsyCryptoSpi;
import xyz.flysium.photon.crypto.impl.SHA256;


/**
 * Unit Test
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class AsyCryptoSpiUnitTest extends CryptoSpiUnitTest {

  protected static final Logger LOGGER = LoggerFactory.getLogger(AsyCryptoSpiUnitTest.class);

  protected void testEncryptAndDecrypt(AsyCryptoSpi spi, String plainText) {
    super.testEncryptAndDecrypt(spi, plainText);
  }

  protected void testSignAndVerify(AsyCryptoSpi spi, String text) {
//    LOGGER.info("-------Java明文文本-------" + text);
//    byte[] textB64 = Util.armor(text.getBytes());
//    byte[] signText = spi.sign(textB64);
//    String signTextHex = Util.hex(signText);
//    LOGGER.info("-------Java签名文本(HEX)-------" + signTextHex);
//    LOGGER.info("-------Java签名文本(HEX)长度-------" + signTextHex.length());
//    boolean suc = spi.verify(textB64, signText);
//    Assert.assertTrue(suc);
    LOGGER.info("-------Java明文文本-------" + text);
    SHA256 sha256 = new SHA256();
    // 明文的信息摘要
    byte[] textHash = sha256.encrypt(text.getBytes());
    LOGGER.info("-------Java明文文本信息摘要(HEX)-------" + CryptoUtil.hex(textHash));
    byte[] signText = spi.sign(textHash);
    String signTextHex = CryptoUtil.hex(signText);
    LOGGER.info("-------Java签名文本(HEX)-------" + signTextHex);
    LOGGER.info("-------Java签名文本(HEX)长度-------" + signTextHex.length());
    boolean suc = spi.verify(textHash, signText);
    Assert.assertTrue(suc);
  }
//
//	protected void testEncryptJsAndDecryptJava(AsyCryptoSpi spi, String plainText,
//			String jsCiperTextB64)
//			throws Exception {
//		LOGGER.info("-------JavaScript加密前明文-------" + plainText);
//		LOGGER.info("-------JavaScript加密后密文-------" + jsCiperTextB64);
//		String decryptedText = spi.decryptStringB64(jsCiperTextB64);
//		LOGGER.info("-------Java解密后明文-------" + decryptedText);
//		Assert.assertEquals(plainText, decryptedText);
//	}


}
