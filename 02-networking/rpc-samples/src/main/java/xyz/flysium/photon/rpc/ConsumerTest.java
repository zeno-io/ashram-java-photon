/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
