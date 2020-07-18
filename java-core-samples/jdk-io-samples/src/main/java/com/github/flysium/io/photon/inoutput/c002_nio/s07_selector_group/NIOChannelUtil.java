package com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group;

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
