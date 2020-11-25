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
import xyz.flysium.photon.crypto.impl.MD5;
import xyz.flysium.photon.crypto.support.CryptoSpiUnitTest;
import xyz.flysium.photon.crypto.support.CryptoUtil;

/**
 * MD5 Test.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class MD5Test extends CryptoSpiUnitTest {

  @Test
  public void test() throws Exception {
    String md5Text = CryptoUtil.hex(md5.encrypt("你好，MD5".getBytes()));
    LOGGER.info("result: {}", md5Text);
  }

  private Digest md5;

  @Before
  public void before() {
    md5 = new MD5();
  }

}
