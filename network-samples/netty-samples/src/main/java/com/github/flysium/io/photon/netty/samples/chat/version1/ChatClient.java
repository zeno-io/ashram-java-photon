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

package com.github.flysium.io.photon.netty.samples.chat.version1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Chat Client.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ChatClient {

  private final String host;
  private final int port;
  private final int pollTimeout;

  private Channel channel = null;
  private final BlockingQueue<String> readyToReadMessages = new LinkedBlockingQueue<>(1024);

  public ChatClient(String host, int port, int pollTimeout) {
    this.host = host;
    this.port = port;
    this.pollTimeout = pollTimeout;
  }

  /**
   * start the client.
   */
  public void start() throws InterruptedException {
    EventLoopGroup workGroup = new NioEventLoopGroup(1);
    try {
      new Bootstrap()
          .group(workGroup)
          .channel(NioSocketChannel.class)
          .option(ChannelOption.TCP_NODELAY, true)
          .option(ChannelOption.SO_LINGER, 100)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline()
                  .addLast(new ClientInBoundHandler());
            }
          })
          .connect(host, port)
          .addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
              if (future.isSuccess()) {
                offerToReadyQueue("Channel is connected !");
                // initialize the channel
                channel = future.channel();
              } else {
                // offer to ready message queue
                offerToReadyQueue("Channel is not connected !");
              }
            }
          }).sync()
          .channel().closeFuture()
          .addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
              if (future.isSuccess()) {
                System.out.println("channel close success");
              } else {
                System.out.println("channel close fail");
              }
              offerToReadyQueue("Channel is closed !");
            }
          })
          .sync();

      offerToReadyQueue("Connection closed !");
    } finally {
      workGroup.shutdownGracefully();
    }
  }

  /**
   * stop the client.
   */
  public void stop() {
    if (channel != null) {
      channel.close();
    }
  }

  /**
   * write to server.
   *
   * @param buff content
   */
  public void sendMessage(String buff) {
    ByteBuf buf = Unpooled.copiedBuffer(buff.getBytes());
    channel.writeAndFlush(buf);
  }

  /**
   * read from server.
   *
   * @return content
   * @throws InterruptedException if interrupted while waiting
   */
  public String readMessage() throws InterruptedException {
    return tryPoll(readyToReadMessages);
  }

  private <T> T tryPoll(BlockingQueue<T> queue) throws InterruptedException {
    if (pollTimeout <= 0) {
      return queue.poll();
    }
    return queue.poll(pollTimeout, TimeUnit.MILLISECONDS);
  }

  /**
   * offer to ready message queue
   */
  private boolean offerToReadyQueue(String readiedString) {
    return readyToReadMessages.offer(readiedString);
  }

  class ClientInBoundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      ByteBuf buf = (ByteBuf) msg;
      try {
        String readiedString = buf.toString(CharsetUtil.UTF_8);
        // offer to ready message queue
        offerToReadyQueue(readiedString);
      } finally {
        ReferenceCountUtil.release(buf);
      }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
        throws Exception {
      cause.printStackTrace();
      ctx.close();
    }
  }

}
