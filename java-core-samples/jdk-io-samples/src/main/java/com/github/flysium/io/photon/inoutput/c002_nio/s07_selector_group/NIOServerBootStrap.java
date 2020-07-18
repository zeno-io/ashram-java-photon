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

package com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group;

import com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group.task.ServerAcceptor;
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

