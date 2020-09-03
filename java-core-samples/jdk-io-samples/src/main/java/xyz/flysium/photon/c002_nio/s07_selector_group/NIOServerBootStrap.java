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

package xyz.flysium.photon.c002_nio.s07_selector_group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.c002_nio.s07_selector_group.task.ServerAcceptor;

/**
 * NIO Server with I/O multiplexing model, multi selector
 *
 * @author Sven Augustus
 */
public class NIOServerBootStrap {


  public static void main(String[] args) throws IOException, InterruptedException {
    NIOEventLoopGroup bossGroup = new NIOEventLoopGroup(1);
    NIOEventLoopGroup workerGroup = new NIOEventLoopGroup(3);

    new NIOServerBootStrap()
        .group(bossGroup, workerGroup)
        .bind(9090);
  }

  protected static final Logger logger = LoggerFactory.getLogger(NIOServerBootStrap.class);

  private NIOEventLoopGroup bossGroup;
  private NIOEventLoopGroup workerGroup;
  private final Map<SocketOption, Object> options = new HashMap<>();
  private final Map<SocketOption, Object> childOptions = new HashMap<>();

  private static final int SOMAXCONN = NetUtil.getSomaxconn();

  public NIOServerBootStrap group(NIOEventLoopGroup group) {
    return group(group, group);
  }

  public NIOServerBootStrap group(NIOEventLoopGroup group,
      NIOEventLoopGroup workerGroup) {
    if (this.bossGroup != null || this.workerGroup != null) {
      throw new IllegalStateException("group set already");
    }
    this.bossGroup = group;
    this.bossGroup.setBootStrap(this);

    this.workerGroup = workerGroup;
    this.workerGroup.setBootStrap(this);

    return this;
  }

  public <T> NIOServerBootStrap option(SocketOption<T> name, T value) {
    if (name != null) {
      this.options.put(name, value);
    }
    return this;
  }

  public <T> NIOServerBootStrap childOption(SocketOption<T> name, T value) {
    if (name != null) {
      this.childOptions.put(name, value);
    }
    return this;
  }

  public NIOServerBootStrap bind(int port) throws IOException {
    ServerSocketChannel server = ServerSocketChannel.open();
    // 设置非阻塞模式
    server.configureBlocking(false);
    // 设置TCP Socket参数
    options.forEach((option, value) -> {
      try {
        server.setOption(option, value);
      } catch (UnsupportedOperationException ignore) {
        // TODO ignore
      } catch (IOException e) {
        logger.warn(e.getMessage(), e);
      }
    });
    // 将 ServerSocket 绑定到特定地址（IP 地址和端口号）
    server.bind(new InetSocketAddress(port), getOption(NIOExtendSocketOptions.SERVER_BACKLOG,
        SOMAXCONN));

    NIOEventLoop loop = bossGroup.choose();
    loop.execute(() -> {
      try {
        final ServerAcceptor acceptor = new ServerAcceptor(workerGroup, server);
        server.register(loop.unwrappedSelector(), SelectionKey.OP_ACCEPT, acceptor);

      } catch (ClosedChannelException e) {
        try {
          server.close();
        } catch (IOException exc) {
          logger.error(exc.getMessage(), exc);
        }
      }
    });
    return this;
  }

  @Override
  public String toString() {
    return "NIOBootstrap{"
        + "backlog=" + getOption(NIOExtendSocketOptions.SERVER_BACKLOG)
        + ", selectorProvider=" + SelectorProvider.provider().getClass().getCanonicalName()
        + '}';
  }

  <T> T getOption(SocketOption<T> name) {
    return (T) options.get(name);
  }

  <T> T getOption(SocketOption<T> name, T defaultValue) {
    T value = getOption(name);
    if (value == null) {
      value = defaultValue;
      options.put(name, value);
    }
    return value;
  }

  <T> T getChildOption(SocketOption<T> name) {
    return (T) childOptions.get(name);
  }

  public Map<SocketOption, Object> getChildOptions() {
    return Collections.unmodifiableMap(childOptions);
  }

}

