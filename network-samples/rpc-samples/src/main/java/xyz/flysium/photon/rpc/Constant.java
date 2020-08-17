/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.photon.rpc;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public final class Constant {

  private Constant() {
  }

  public static final String HOST = "127.0.0.1";

  public static final int CONSUMER_PORT = 19091;

  public static final int PROVIDER_PORT = 29095;

  public static final int CONSUMER_POOL_SIZE = 1;

  public static final int PROVIDER_SLEEP_TIME = 0;

}
