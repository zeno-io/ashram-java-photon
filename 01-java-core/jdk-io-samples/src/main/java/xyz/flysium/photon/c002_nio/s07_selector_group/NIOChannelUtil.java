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
import java.net.SocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NIO Channel Utils.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public final class NIOChannelUtil {

  private NIOChannelUtil() {
  }

  static final Logger logger = LoggerFactory.getLogger(NIOChannelUtil.class);

  public static void close(Channel channel) {
    try {
      channel.close();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  public static void closeServer(ServerSocketChannel server) {
    if (logger.isWarnEnabled()) {
      logger.warn("close server：" + getLocalAddress(server));
    }
    close(server);
  }

  public static void closeClient(SocketChannel client) {
    if (logger.isWarnEnabled()) {
      logger.warn("close client：" + getRemoteAddress(client));
    }
    close(client);
  }

  public static SocketAddress getLocalAddress(ServerSocketChannel server) {
    try {
      return server.getLocalAddress();
    } catch (IOException e) {
      // ignore
    }
    return null;
  }

  public static SocketAddress getRemoteAddress(SocketChannel client) {
    try {
      return client.getRemoteAddress();
    } catch (IOException e) {
      // ignore
    }
    return null;
  }

}
