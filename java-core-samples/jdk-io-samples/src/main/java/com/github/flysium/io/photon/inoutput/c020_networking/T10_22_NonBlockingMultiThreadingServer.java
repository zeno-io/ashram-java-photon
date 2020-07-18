package com.github.flysium.io.photon.inoutput.c020_networking;

import com.github.flysium.io.photon.inoutput.c002_nio.s05_nonblocking.NonBlockingMultiThreadingServer;
import java.io.IOException;

/**
 * non-blocking I/O Server with multi-threading
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T10_22_NonBlockingMultiThreadingServer {

  public static void main(String[] args) throws IOException {
    NonBlockingMultiThreadingServer server = new NonBlockingMultiThreadingServer(
        T10_0_C10KClient.SERVER_PORT, T10_0_C10KClient.SERVER_BACKLOG,
        T10_0_C10KClient.SERVER_EXECUTOR);
    System.out.println("server configuration: " + server.toString());
    server.start();
  }

}
