package com.github.flysium.io.photon.inoutput.c020_networking;

import com.github.flysium.io.photon.inoutput.c002_nio.s05_nonblocking.NonBlockingServer;
import java.io.IOException;

/**
 * non-blocking I/O Server
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T10_21_NonBlockingServer {

  public static void main(String[] args) throws IOException {
    NonBlockingServer server = new NonBlockingServer(
        T10_0_C10KClient.SERVER_PORT, T10_0_C10KClient.SERVER_BACKLOG);
    System.out.println("server configuration: " + server.toString());
    server.start();
  }

}
