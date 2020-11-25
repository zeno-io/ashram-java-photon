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
import xyz.flysium.photon.crypto.impl.RSA;
import xyz.flysium.photon.crypto.support.AsyCryptoSpiUnitTest;

/**
 * RSA Test.
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public class RSAUnitTest2 extends AsyCryptoSpiUnitTest {

  private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC875nF9is7j7LRpI+sSdNukzK/RqbUOfjqqNxcPR4GcF8sN1yQkeiOJSRmJm6/Y9/C6U4IyuKWzW6Z1B1hIsabv4nXnOsmQTsxc94FG3S2/md0t4Rd9swaX0qwke0vu2y0EQR1YLOcWpOYqBJ4DukMuxP9Sk7yO8PM6m2X6g3PeQIDAQAB";

  private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALzvmcX2KzuPstGkj6xJ026TMr9GptQ5+Oqo3Fw9HgZwXyw3XJCR6I4lJGYmbr9j38LpTgjK4pbNbpnUHWEixpu/idec6yZBOzFz3gUbdLb+Z3S3hF32zBpfSrCR7S+7bLQRBHVgs5xak5ioEngO6Qy7E/1KTvI7w8zqbZfqDc95AgMBAAECgYBSfPWRNYH4liuHViaYHP9fpoqgcjNCMzFXGnRjHRfvzb3vqxgR0nJLJTI4Gw19KBi4m23DbvjwyC5CoRZWo308IHuDHukv+XVYpGP7e9wr3IZXQBV0qg7DADk6n5dO4pmq+vC8N1vXtZIOSj0QnnTXmFcganG59GMQEGWULwWPxQJBAOSsRS/CRXNmsmtU25F/VLqNf1j/b2WBqw4LJBE8SsZaIUeGZsz+W8O3p8fvonW7vpk9x5Mzna2+5fmpn+WnXL8CQQDTg6rUegc+UU5EBMipkkD3k5n2ikHy5gW3XfYEHSO/gISFKGyJj8XVP6EjLDG2OURQ0eG0+eIF/359Q15fzgnHAkAUSxV4v4zaSOrci5NQvjQOui/q/gAsye0cOfDhdrBMGgOKDWtiFdCe9dV18NZCDH9Fqi3j6li76o0OMvd1sfyRAkBz9enAV66E8dpw3BaPMQnBMgDk82O4oXdc0nzAQKGKqhD9V+45dMNHaH0VZXNTrg3QKWEO8TiDhVF5p2ekKredAkEAskkSuR6wbuOBdd0hadLLvRKzkKNwLb0PyFeI7a2g6AZSY1A/xxE3o0KguyFJR1crDvAwO/VXnb6/rf2Jcws/rQ==";

  @Test
  public void testEncryptJsAndDecryptJava() throws Exception {
    String plainText = "Javascript中文";
    String jsCiperTextB64 = "ce4ApO9VnxdPT5HkTr7v8e3+DK/YNj+eW0sKXq78Lk4S0SOSnvmx6l98GUuEeErtoiehXPH6sRAYBkGM+OkVkgGYPGw2JhOm/IRx+EeAWM3WeW3caUOYynTOGDPXwFYUoBEDA0iER0i8b5VATVXypUwDp8vDnm0XVI3/WS4W98o=";
    testEncryptJsAndDecryptJava(rsa, plainText, jsCiperTextB64);
  }

  private AsyCryptoSpi rsa;

  @Before
  public void before() {
    rsa = new RSA();
    // rsa = new RSA(new BouncyCastleProvider(), "RSA/NONE/PKCS1Padding");
    rsa.setPublicKey(PUBLIC_KEY);
    rsa.setPrivateKey(PRIVATE_KEY);
  }

}
