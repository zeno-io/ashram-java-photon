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

package xyz.flysium.photon.rpc.service;

import java.util.concurrent.TimeUnit;
import xyz.flysium.photon.rpc.Constant;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class EchoServiceImpl implements EchoService {

  @Override
  public String echo(String message) {
    try {
      if (Constant.PROVIDER_SLEEP_TIME > 0) {
        TimeUnit.MILLISECONDS.sleep(Constant.PROVIDER_SLEEP_TIME);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return message;
  }

}
