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

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import xyz.flysium.photon.rpc.container.RpcContainer;
import xyz.flysium.photon.rpc.service.EchoService;
import xyz.flysium.photon.rpc.service.EchoServiceImpl;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class ProviderTest {

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, InstantiationException, IllegalAccessException, IOException {
    RpcContainer container = new RpcContainer(Constant.PROVIDER_PORT);
    container.start();
    container.registerBean(EchoService.class, EchoServiceImpl.class);

    System.in.read();

    System.out.println("------------------> shutdown");
    container.shutdown();
  }

}
