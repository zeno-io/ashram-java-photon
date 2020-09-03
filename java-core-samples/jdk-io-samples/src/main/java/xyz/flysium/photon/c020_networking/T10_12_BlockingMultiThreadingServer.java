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

package xyz.flysium.photon.c020_networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import xyz.flysium.photon.c000_bio.s08_socket.SocketMultiThreadingServer;

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
