/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.lang.reference;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class SoftReferenceTest {

  public static void main(String[] args) throws InterruptedException {
    System.out.println(new SoftReferenceTest().get());
  }

  private static SoftReference<Map<String, Object>> softReference = new SoftReference<Map<String, Object>>(
      new HashMap<String, Object>());

  public Map<String, Object> get() {

    Map<String, Object> map = softReference.get();
    if (map == null) {
      map = new HashMap<String, Object>(8);
      softReference = new SoftReference<Map<String, Object>>(map);
    }
    return map;
  }

}
