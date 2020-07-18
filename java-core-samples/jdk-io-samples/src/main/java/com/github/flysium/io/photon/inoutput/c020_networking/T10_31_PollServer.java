package com.github.flysium.io.photon.inoutput.c020_networking;

import com.github.flysium.io.photon.inoutput.c002_nio.s06_selector.NIOMultiplexingServer;
import java.io.IOException;

/**
 * NIO Server with I/O multiplexing model (poll)
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T10_31_PollServer {

  public static void main(String[] args) throws IOException {
    //    -Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.PollSelectorProvider
    System.setProperty("java.nio.channels.spi.SelectorProvider", "sun.nio.ch.PollSelectorProvider");

    NIOMultiplexingServer server = new NIOMultiplexingServer(T10_0_C10KClient.SERVER_PORT,
        T10_0_C10KClient.SERVER_BACKLOG);
    System.out.println("server configuration: " + server.toString());
    server.start();
  }

}
