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
import xyz.flysium.photon.crypto.impl.RSA;
import xyz.flysium.photon.crypto.support.AsyCryptoSpiUnitTest;

/**
 * RSA Test.
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class RSAUnitTest extends AsyCryptoSpiUnitTest {

  @Test
  public void testEncryptAndDecrypt1() {
    AsyCryptoSpi rsa = new RSA();
    rsa.generateKey(1024);

    String plainText = "4ba042f9792ea957b1f3fea42fe74276d05ac011e863c4072191d24231a879ca";
    testEncryptAndDecrypt(rsa, plainText);
  }

  @Test
  public void testEncryptAndDecrypt2() {
    AsyCryptoSpi rsa = new RSA();
    rsa.generateKey(1024);

    String plainText = "Javascript中文";
    testEncryptAndDecrypt(rsa, plainText);
  }

  @Test
  public void testEncryptAndDecrypt3() {
    AsyCryptoSpi rsa = new RSA();
    rsa.generateKey();

    String plainText = "赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。十步杀一人，千里不留行。事了拂衣去，深藏身与名。闲过信陵饮，脱剑膝前横。将炙啖朱亥，持觞劝侯嬴。三杯吐然诺，五岳倒为轻。眼花耳热后，意气素霓生。救赵挥金锤，邯郸先震惊。千秋二壮士，烜赫大梁城。纵死侠骨香，不惭世上英。谁能书阁下，白首太玄经。";
    testEncryptAndDecrypt(rsa, plainText);
  }

  @Test
  public void testSignAndVerify1() {
    AsyCryptoSpi rsa = new RSA();
    rsa.generateKey();

    String text = "4ba042f9792ea957b1f3fea42fe74276d05ac011e863c4072191d24231a879ca";
    testSignAndVerify(rsa, text);
  }

  @Test
  public void testSignAndVerify2() {
    AsyCryptoSpi rsa = new RSA();
    rsa.generateKey();

    String text = "Javascript中文";
    testSignAndVerify(rsa, text);
  }

  @Test
  public void testSignAndVerify3() {
    AsyCryptoSpi rsa = new RSA();
    rsa.generateKey();

    String text = "赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。十步杀一人，千里不留行。事了拂衣去，深藏身与名。闲过信陵饮，脱剑膝前横。将炙啖朱亥，持觞劝侯嬴。三杯吐然诺，五岳倒为轻。眼花耳热后，意气素霓生。救赵挥金锤，邯郸先震惊。千秋二壮士，烜赫大梁城。纵死侠骨香，不惭世上英。谁能书阁下，白首太玄经。";
    testSignAndVerify(rsa, text);
  }


}
