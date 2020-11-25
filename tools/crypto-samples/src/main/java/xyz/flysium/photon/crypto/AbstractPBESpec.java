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

import java.security.Provider;

/**
 * Specifies the set of parameters used with Password-Based Encryption (PBE), which is defined in
 * the PKCS #5 standard.
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.7
 */
public abstract class AbstractPBESpec extends AbstractSecretSpi {

  public AbstractPBESpec(String algorithm, Provider provider) {
    super(algorithm, provider, false, true);
  }

}
