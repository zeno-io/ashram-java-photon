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


import org.junit.Before;
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
public class DESUnitTest2 extends CryptoSpiUnitTest {

  // 前端JavaScript（CryptoJS 3.1.2）采用 CryptoJS.mode.ECB 、CryptoJS.pad.Pkcs7
  // PKCS#5和PKCS#7是一样的padding方式
  // 故此这里采用 转换名：DES/ECB/PKCS5Padding
  private final String transforms = "DES/ECB/PKCS5Padding";
  private final String KEY = "0302b20";

  @Test
  public void test() throws Exception {
    String plainText = "This is a test!";
    String jsCiperTextB64 = "PDYGU9LKjj69X33rsdWjZA==";
    testEncryptJsAndDecryptJava(des, plainText, jsCiperTextB64);
  }

  private CryptoSpi des;

  @Before
  public void before() {
    //des = new DES();
    des = new DES(transforms);
    des.setSecret(KEY.getBytes());
    LOGGER.info("-------密钥长度-------" + (des.getSecret().length * 8));
  }

}
