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

package xyz.flysium.photon.c020_networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <li>C10k problem: <a>http://www.kegel.com/c10k.html</a></li>
 *
 * <li>
 * 0.环境准备
 * <pre>
 *     # 注意这里的enp70s0 替换为你本机可用的网卡名称
 *     ifconfig enp70s0:1 10.0.5.1
 *     ifconfig enp70s0:2 10.0.5.2
 * </pre>
 * </li>
 * <li>
 * 1-1.测试 阻塞IO模型（Blocking I/O） 单线程
 * <p>
 * 启动 {@link T10_11_BlockingServer}, {@link T10_0_C10KClient} ， 等待一定时间
 * <pre>
 *     netstat -tnap | grep 127.0.0.1:9090 | wc -l
 *     jps
 *     lsof -op 12645 | wc -l
 * </pre>
 * </li>
 * <li>
 * 1-2.测试 阻塞IO模型（Blocking I/O） 多线程
 * <p>
 * 启动 {@link T10_12_BlockingMultiThreadingServer}, {@link T10_0_C10KClient}， 等待一定时间
 * </li>
 * <li>
 * 2-1.测试 非阻塞IO模型（non-blocking I/O） 单线程
 * <p>
 * 启动 {@link T10_21_NonBlockingServer},  {@link T10_0_C10KClient} ，等待一定时间
 * </li>
 * <li>
 * 2-2.测试 非阻塞IO模型（non-blocking I/O） 多线程
 * <p>
 * 启动 {@link T10_22_NonBlockingMultiThreadingServer},  {@link T10_0_C10KClient} ，等待一定时间
 * </li>
 * <li>
 * 3-1.测试 IO复用模型（I/O multiplexing） POLL 单线程
 * <p>
 * 启动 {@link T10_31_PollServer},  {@link T10_0_C10KClient} ， 等待一定时间
 * </li>
 * <li>
 * 3-2.测试 IO复用模型（I/O multiplexing） POLL 多线程
 * <p>
 * 启动 {@link T10_32_PollMultiTheadingServer} , {@link T10_0_C10KClient}， 等待一定时间
 * </li>
 * <li>
 * 3-3.测试 IO复用模型（I/O multiplexing） EPOLL 多线程
 * <p>
 * 启动 {@link T10_33_EpollMultiTheadingServer},  {@link T10_0_C10KClient} ， 等待一定时间
 * </li>
 * <li>
 * 3-4.测试 IO复用模型（I/O multiplexing） EPOLL 多线程 , 多Selector
 * <p>
 * 启动 {@link T10_34_EpollMultiTheadingGroupServer},  {@link T10_0_C10KClient} ， 等待一定时间
 * </li>
 * <li>
 * 3-5.测试 Netty
 * <p>
 * 启动 {@link T10_35_NettyServer},  {@link T10_0_C10KClient} ， 等待一定时间
 * </li>
 * <li>
 * 4.环境清理
 * <pre>
 *     ifconfig enp70s0:1 down # 注意这里的enp70s0 替换为你本机可用的网卡名称
 * </pre>
 * </li>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T10_0_C10KClient {

  private static final int START_PORT = 10_000;
  private static final int END_PORT = 65_000;

  // 定义测试中服务器的参数
  public static final int SERVER_PORT = 9090;
  public static final int SERVER_BACKLOG = 100;
  public static final int SERVER_READ_TIMEOUT = 50;
  public static final ThreadPoolExecutor SERVER_EXECUTOR = new ThreadPoolExecutor(4, 8,
      60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>((END_PORT - START_PORT) * 2),
      new ThreadPoolExecutor.CallerRunsPolicy());

  public static void main(String[] args) {
    long start = Instant.now().toEpochMilli();
    List<T10_0_C10KClient> clients = new LinkedList<>();

    for (int i = START_PORT; i < END_PORT; i++) {
      T10_0_C10KClient client1 = new T10_0_C10KClient("127.0.0.1", SERVER_PORT, "10.0.5.1", i);
      if (client1.connect()) {
        clients.add(client1);
      }

      T10_0_C10KClient client2 = new T10_0_C10KClient("127.0.0.1", SERVER_PORT, "10.0.5.2", i);
      if (client2.connect()) {
        clients.add(client2);
      }
      if ((clients.size() < 1000 && clients.size() % 10 == 0)
          || (clients.size() > 1000 && clients.size() % 100 == 0)) {
        System.out.println(humanString(clients.size(), Instant.now().toEpochMilli() - start));
      }
    }
    System.out.println(humanString(clients.size(), Instant.now().toEpochMilli() - start));

    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String humanString(long size, long millis) {
    return "clients size=" + humanSizeString(size) + ", cost=" + humanCostString(millis)
        + ", speed=" + String.format("%.2f op/ms", (size * 1.0 / millis));
  }

  private static String humanSizeString(long size) {
    if (size < 10000) {
      return size + "";
    }
    long s = size / 10000;
    return s + "w," + (size - 10000 * s) + "";
  }

  private static String humanCostString(long millis) {
    if (millis < 1000) {
      return millis + "ms";
    }
    long s = millis / 1000;
    return s + "," + (millis - 1000 * s) + "ms";
  }

  private final String host;
  private final int port;
  private final String bindLocalIp;
  private final int bindLocalPort;

  public T10_0_C10KClient(String host, int port, String bindLocalIp, int bindLocalPort) {
    this.host = host;
    this.port = port;
    this.bindLocalIp = bindLocalIp;
    this.bindLocalPort = bindLocalPort;
  }

  public boolean connect() {
    try {
      SocketChannel client = SocketChannel.open();

      if (!"".equals(bindLocalIp) && bindLocalPort > 0) {
        client.bind(new InetSocketAddress(bindLocalIp, bindLocalPort));
      }

      return client.connect(new InetSocketAddress(host, port));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

}
