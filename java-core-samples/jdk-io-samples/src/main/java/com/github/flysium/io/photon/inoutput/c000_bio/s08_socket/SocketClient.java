/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.inoutput.c000_bio.s08_socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client
 *
 * @author Sven Augustus
 */
public class SocketClient implements Runnable {

  public static void main(String[] args) throws Exception {
    new Thread(new SocketClient("127.0.0.1", 9090, () -> "测试1"), "BIOClient-001").start();
    new Thread(new SocketClient("127.0.0.1", 9090, () -> "测试2"), "BIOClient-002").start();
    new Thread(new SocketClient("127.0.0.1", 9090, () -> "测试3"), "BIOClient-003").start();
    new Thread(new SocketClient("127.0.0.1", 9090, () -> "测试4"), "BIOClient-004").start();

    System.in.read();
  }

  private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
  private final String host;
  private final int port;

  // for test
  private final Supplier<String> supplier;

  private Socket client;

  public SocketClient(String host, int port, Supplier<String> supplier) {
    this.host = host;
    this.port = port;
    this.supplier = supplier;
  }

  @Override
  public void run() {
    try {
      init();

      setSocketOptions(client);

      readyWrite();

      readyRead();

    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    } finally {
      close();
    }
  }

  private void init() throws IOException {
    client = new Socket(host, port);
  }

  protected void setSocketOptions(Socket client) throws SocketException {
    client.setTcpNoDelay(true);
    client.setSoLinger(true, 100);
//    client.setSoTimeout(5000);
  }

  protected void readyWrite() throws IOException {
    if (supplier == null) {
      return;
    }
    String buf = supplier.get();

    logger.info("write...");

    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    bw.write(buf);
    bw.flush();
  }

  protected void readyRead() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
    char[] cbuf = new char[8092];
    while (true) {
      int readCount = br.read(cbuf);
      if (readCount > 0) {
        String response = new String(cbuf, 0, readCount);
        logger.info("readied something, count: " + readCount + " data: " + response);
      } else if (readCount == 0) {
        logger.warn("readied nothing ! ");
      } else {
        logger.warn("readied -1...");
        close();
        break;
      }
    }
  }

  protected void close() {
    logger.warn("close.....");
    try {
      client.close();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

}
