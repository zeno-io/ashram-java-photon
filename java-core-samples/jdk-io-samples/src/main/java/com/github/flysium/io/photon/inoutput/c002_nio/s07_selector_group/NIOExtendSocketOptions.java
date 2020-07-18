package com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group;

import java.net.SocketOption;

/**
 * NIO Extend Socket Options
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class NIOExtendSocketOptions {

  public static final SocketOption<Integer> SERVER_BACKLOG =
      new ExtendSocketOption<Integer>("SERVER_BACKLOG", Integer.class);

  private static class ExtendSocketOption<T> implements SocketOption<T> {

    private final String name;
    private final Class<T> type;

    ExtendSocketOption(String name, Class<T> type) {
      this.name = name;
      this.type = type;
    }

    @Override
    public String name() {
      return name;
    }

    @Override
    public Class<T> type() {
      return type;
    }

    @Override
    public String toString() {
      return name;
    }
  }

}
