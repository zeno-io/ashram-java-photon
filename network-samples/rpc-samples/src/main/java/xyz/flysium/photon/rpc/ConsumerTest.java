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

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.rpc.container.RpcContainer;
import xyz.flysium.photon.rpc.proxy.ProxyFactory;
import xyz.flysium.photon.rpc.service.EchoService;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class ConsumerTest {

  private static final int THREADS = 1000;
  private static final CountDownLatch LATCH = new CountDownLatch(THREADS);
  private static Logger logger = LoggerFactory.getLogger(ConsumerTest.class);

  public static void main(String[] args)
      throws InterruptedException, ExecutionException, IllegalAccessException, InstantiationException {
    RpcContainer container = new RpcContainer(Constant.CONSUMER_PORT);
    container.start();
//   container.registerBean(EchoService.class, EchoServiceImpl.class);
    container.registerDiscovery(EchoService.class,
        new InetSocketAddress(Constant.HOST, Constant.PROVIDER_PORT));

    AtomicInteger num = new AtomicInteger(1);
    for (int i = 0; i < THREADS; i++) {
      new Thread(() -> {
        try {
          EchoService echoService = ProxyFactory.getProxy(EchoService.class);
          afterExecute(echoService.echo("rpc test " + num.getAndIncrement()));
          afterExecute(echoService.echo("rpc test  " + num.getAndIncrement()));
          afterExecute(echoService.echo("rpc test  " + num.getAndIncrement()));
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        } finally {
          LATCH.countDown();
        }
      }).start();
    }
    LATCH.await();

    System.out.println("------------------> shutdown");
    container.shutdown();
  }

  private static void afterExecute(Object result) {
    //  logger.info(result.toString());
  }

}
