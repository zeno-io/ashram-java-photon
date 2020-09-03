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

package xyz.flysium.photon.c000_bio.s08_socket;

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
