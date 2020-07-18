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

package com.github.flysium.io.photon.inoutput.c003_aio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client
 *
 * @author Sven Augustus
 */
public class AIOClient implements Runnable {

  public static void main(String[] args) throws Exception {
    Supplier<String> supplier = () -> {
      return "" + new Random().nextInt(1000);
    };

    new Thread(new AIOClient("127.0.0.1", 9090, supplier), "AIOClient-001").start();
    new Thread(new AIOClient("127.0.0.1", 9090, supplier), "AIOClient-002").start();
    new Thread(new AIOClient("127.0.0.1", 9090, supplier), "AIOClient-003").start();
    new Thread(new AIOClient("127.0.0.1", 9090, supplier), "AIOClient-004").start();

    System.in.read();
  }

  private static final Logger logger = LoggerFactory.getLogger(AIOClient.class);
  private final String host;
  private final int port;

  // for test
  private final Supplier<String> supplier;

  private AsynchronousSocketChannel client;

  public AIOClient(String host, int port, Supplier<String> supplier) {
    this.host = host;
    this.port = port;
    this.supplier = supplier;
  }

  @Override
  public void run() {
    try {
      init();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  private void init() throws IOException {
    try {
      // 初始化一个AsynchronousSocketChannel
      client = AsynchronousSocketChannel.open();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }

    setSocketOptions(client);

    // 连接服务端，并将自身作为连接成功时的回调handler
    client.connect(new InetSocketAddress(host, port), this, new ConnectedHandler());
  }

  protected void setSocketOptions(AsynchronousSocketChannel client) throws IOException {
    client.setOption(StandardSocketOptions.TCP_NODELAY, true);
    client.setOption(StandardSocketOptions.SO_LINGER, 100);
  }

  protected void readyWrite() {
    if (supplier == null) {
      return;
    }
    String buf = supplier.get();

    logger.info("write...");

    // 分配写缓存区
    ByteBuffer write = ByteBuffer.wrap(buf.getBytes());
    // 将缓存中的数据写到channel，同时使用匿名内部类做完成后回调
    client.write(write, write, new WriteHandler());
  }

  protected void readyRead(Integer readCount, ByteBuffer buffer) {
    if (readCount > 0) {
      buffer.flip();
      byte[] response = new byte[buffer.remaining()];
      buffer.get(response);
      String responseString = new String(response, StandardCharsets.UTF_8);

      logger.info("readied something, count: " + readCount + " data: " + responseString);
    } else if (readCount == 0) {
      logger.warn("readied nothing ! ");
    } else {
      logger.warn("readied -1...");
      close();
    }
  }

  class ConnectedHandler implements CompletionHandler<Void, AIOClient> {

    /**
     * 连接服务端成功时的回调
     */
    @Override
    public void completed(Void result, AIOClient attachment) {
      readyWrite();
    }

    /**
     * 连接服务端失败
     */
    @Override
    public void failed(Throwable exc, AIOClient attachment) {
      logger.error(exc.getMessage(), exc);
      close();
    }
  }

  class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {

    @Override
    public void completed(Integer result, ByteBuffer byteBuffer) {
      // 如果缓存数据中还有数据，接着写
      if (byteBuffer.hasRemaining()) {
        client.write(byteBuffer, byteBuffer, this);
      } else {
        ByteBuffer readBuffer = ByteBuffer.allocate(8192);
        //读取服务端的返回到缓存，采用匿名内部类做写完缓存后的回调handler
        client.read(readBuffer, readBuffer, new ReadHandler());
      }

    }

    /**
     * 缓存写入channel失败 关闭client，释放channel相关联的一切资源
     */
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
      logger.error(exc.getMessage(), exc);
      close();
    }
  }

  class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    /**
     * 从缓存中读取数据，做业务处理
     */
    @Override
    public void completed(Integer readCount, ByteBuffer buffer) {
      readyRead(readCount, buffer);
    }

    /**
     * 从缓存读取数据失败 关闭client，释放channel相关联的一切资源
     */
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
      logger.error(exc.getMessage(), exc);
      close();
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

  private static SocketAddress getLocalAddress(AsynchronousSocketChannel channel) {
    try {
      return channel.getLocalAddress();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
