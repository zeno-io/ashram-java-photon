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

package xyz.flysium.photon.c002_nio.s06_selector;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadPoolExecutor;
import xyz.flysium.photon.c002_nio.s05_nonblocking.NonBlockingServer;

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
    this(port, backlog, NonBlockingServer.defaultThreadPoolExecutor(1000));
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
