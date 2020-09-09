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

package xyz.flysium.photon.c002_nio.s05_nonblocking;

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
