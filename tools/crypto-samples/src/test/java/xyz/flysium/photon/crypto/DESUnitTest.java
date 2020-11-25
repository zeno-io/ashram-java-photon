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

import org.junit.Test;
import xyz.flysium.photon.crypto.impl.DES;
import xyz.flysium.photon.crypto.support.CryptoSpiUnitTest;

/**
 * DES Test.
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class DESUnitTest extends CryptoSpiUnitTest {

  @Test
  public void test() {
    CryptoSpi des = new DES("DES/ECB/PKCS5Padding");
    des.generateKey(56);
    LOGGER.info("-------密钥长度-------" + (des.getSecret().length * 8));

    String plainText = "Message";
    testEncryptAndDecrypt(des, plainText);
  }

}
