package com.github.flysium.io.photon.inoutput.c020_networking;

import com.github.flysium.io.photon.inoutput.c000_bio.s08_socket.SocketMultiThreadingServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Blocking I/O server with multi-threading
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T10_12_BlockingMultiThreadingServer {

  public static void main(String[] args) throws IOException {
    SocketMultiThreadingServer server = new SocketMultiThreadingServer(
        T10_0_C10KClient.SERVER_PORT, T10_0_C10KClient.SERVER_BACKLOG,
        T10_0_C10KClient.SERVER_EXECUTOR) {
      @Override
      protected void setServerSocketOptions(ServerSocket server) throws SocketException {
        super.setServerSocketOptions(server);
        // FIXME: 当设置超时时间，JDK 中 其实改为 POLL，不再是Blocking I/O，
        //  系统调用：poll([{fd=10, events=POLLIN|POLLERR}], 1, 5) = 0 (Timeout)
        // server.setSoTimeout(T10_0_C10KClient.SERVER_ACCEPT_TIMEOUT);
      }

      @Override
      protected void setClientSocketOptions(Socket client) throws SocketException {
        super.setClientSocketOptions(client);
        // FIXME: 当设置超时时间，JDK 中 其实改为 POLL，不再是Blocking I/O，
        //  系统调用：poll([{fd=10, events=POLLIN|POLLERR}], 1, 5) = 0 (Timeout)
        // client.setSoTimeout(T10_0_C10KClient.SERVER_READ_TIMEOUT);
      }
    };
    System.out.println("server configuration: " + server.toString());
    server.start();
  }

}
