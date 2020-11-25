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
import xyz.flysium.photon.crypto.impl.AES;
import xyz.flysium.photon.crypto.support.CryptoSpiUnitTest;

/**
 * AES Test.
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class AESUnitTest extends CryptoSpiUnitTest {

  @Test
  public void test() {
    CryptoSpi aes = new AES();
    //aes = new AES("AES/ECB/PKCS5Padding");
    aes.generateKey(128);

    String plainText = "JavaScript中文";
    testEncryptAndDecrypt(aes, plainText);
  }

}
