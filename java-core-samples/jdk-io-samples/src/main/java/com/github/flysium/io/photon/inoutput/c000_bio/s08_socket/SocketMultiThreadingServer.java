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

package com.github.flysium.io.photon.inoutput.c000_bio.s08_socket;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Blocking I/O server with multi-threading
 *
 * @author Sven Augustus
 */
public class SocketMultiThreadingServer extends SocketServer {

  public static void main(String[] args) throws IOException {
    SocketMultiThreadingServer server = new SocketMultiThreadingServer(9090);
    server.start();
  }

  protected final ThreadPoolExecutor executor;

  public SocketMultiThreadingServer(int port) {
    this(port, 50, defaultThreadPoolExecutor(1000));
  }

  public SocketMultiThreadingServer(int port, int backlog,
      ThreadPoolExecutor threadPoolExecutor) {
    super(port, backlog);
    this.executor = threadPoolExecutor;
  }

  @Override
  public String toString() {
    return "SocketMultiThreadingServer{" + "port=" + port
        + ", backlog=" + backlog
        + ", threadQueueCapacity=" + this.executor.getQueue().remainingCapacity()
        + '}';
  }

  protected static ThreadPoolExecutor defaultThreadPoolExecutor(int threadQueueCapacity) {
    int coreMinSize = Runtime.getRuntime().availableProcessors() / 2;
    int coreMaxSize = Math.max(coreMinSize, 8);
    return new ThreadPoolExecutor(coreMinSize, coreMaxSize,
        60, TimeUnit.SECONDS,
        threadQueueCapacity <= 0 ? new SynchronousQueue<>()
            : new LinkedBlockingQueue<Runnable>(threadQueueCapacity),
        new ThreadPoolExecutor.CallerRunsPolicy());
  }

  /**
   * 涉及系统调用（创建线程）：
   * <pre>
   *   clone(child_stack=0x7fb1d0415fb0, flags=CLONE_VM|CLONE_FS|CLONE_FILES|CLONE_SIGHAND|CLONE_THREAD|CLONE_SYSVSEM|CLONE_SETTLS|CLONE_PARENT_SETTID|CLONE_CHILD_CLEARTID, parent_tid=[14809], tls=0x7fb1d0416700, child_tidptr=0x7fb1d04169d0) = 14809
   * </pre>
   */
  @Override
  protected void readyRead(Socket client) {
    executor.submit(() -> doRead(client));
  }

}
