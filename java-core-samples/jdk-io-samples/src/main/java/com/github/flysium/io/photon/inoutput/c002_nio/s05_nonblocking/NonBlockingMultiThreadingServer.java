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

package com.github.flysium.io.photon.inoutput.c002_nio.s05_nonblocking;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * non-blocking I/O Server with multi-threading
 *
 * @author Sven Augustus
 */
public class NonBlockingMultiThreadingServer extends NonBlockingServer {

  public static void main(String[] args) throws Exception {
    NonBlockingMultiThreadingServer nioServer = new NonBlockingMultiThreadingServer(9090);
    nioServer.start();
  }

  protected final ThreadPoolExecutor executor;

  public NonBlockingMultiThreadingServer(int port) {
    this(port, 50, 1000);
  }

  public NonBlockingMultiThreadingServer(int port, int backlog,
      int threadQueueCapacity) {
    this(port, backlog, defaultThreadPoolExecutor(1000));
  }

  public NonBlockingMultiThreadingServer(int port, int backlog,
      ThreadPoolExecutor threadPoolExecutor) {
    super(port, backlog);
    this.executor = threadPoolExecutor;
  }

  @Override
  public String toString() {
    return "NonBlockingMultiThreadingServer{" + "port=" + port
        + ", backlog=" + backlog
        + ", threadQueueCapacity=" + executor.getQueue().remainingCapacity()
        + '}';
  }

  @Override
  protected void readyRead(SocketChannel newClient) {
    if (newClient != null) {
      executor.submit(() -> this.doRead(newClient, false, false));
    }
  }

}
