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

package com.github.flysium.io.photon.inoutput.c002_nio.s06_selector;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * NIO Server with I/O multiplexing model, multi-threading, single selector
 *
 * @author Sven Augustus
 */
public class NIOMultiplexingThreadingServer extends NIOMultiplexingServer {

  public static void main(String[] args) throws IOException {
    NIOMultiplexingThreadingServer server = new NIOMultiplexingThreadingServer(9090);
    server.start();
  }

  protected final ThreadPoolExecutor executor;

  public NIOMultiplexingThreadingServer(int port) {
    this(port, 50);
  }

  public NIOMultiplexingThreadingServer(int port, int backlog) {
    this(port, backlog, defaultThreadPoolExecutor(1000));
  }

  public NIOMultiplexingThreadingServer(int port, int backlog,
      ThreadPoolExecutor threadPoolExecutor) {
    super(port, backlog);
    this.executor = threadPoolExecutor;
  }

  @Override
  public String toString() {
    return "NIOMultiplexingThreadingServer{" + "port=" + port
        + ", backlog=" + backlog
        + ", selectorProvider="
        + SelectorProvider.provider().getClass().getCanonicalName()
        + ", threadQueueCapacity=" + executor.getQueue().remainingCapacity()
        + '}';
  }

  @Override
  protected void readyRead(SelectionKey key) {
    // 在主线程里不能阻塞执行，不能是线性的， 所以事件会被重复触发, 使用 key.cancel 解决
    // epoll情况，系统调用：epoll_ctl(14, EPOLL_CTL_DEL, 17, 0x7f94c4fd64c4) = 0
    key.cancel();
    this.executor.submit(() -> doRead(key));
  }

  @Override
  protected void readyWrite(SelectionKey key) {
    // 在主线程里不能阻塞执行，不能是线性的， 所以事件会被重复触发, 使用 key.cancel 解决
    // epoll情况，系统调用：epoll_ctl(14, EPOLL_CTL_DEL, 17, 0x7f94c4fd64c4) = 0
    key.cancel();
    this.executor.submit(() -> toWrite(key));
  }

}
