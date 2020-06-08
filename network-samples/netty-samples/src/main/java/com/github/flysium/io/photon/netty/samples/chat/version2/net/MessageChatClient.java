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

package com.github.flysium.io.photon.netty.samples.chat.version2.net;

import com.github.flysium.io.photon.netty.samples.chat.version2.model.InstantMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Chat Client.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class MessageChatClient {

  private final String host;
  private final int port;
  private final int pollTimeout;

  private final String userId;

  private Channel channel = null;
  private final BlockingQueue<InstantMessage> readyToReadMessages = new LinkedBlockingQueue<>(1024);

  public MessageChatClient(String host, int port, int pollTimeout, String userId) {
    this.host = host;
    this.port = port;
    this.pollTimeout = pollTimeout;
    this.userId = userId;
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
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline()
                  .addLast(new InstantMessageDecoder())
                  .addLast(new InstantMessageEncoder())
                  .addLast(new ClientHandler());
            }
          })
          .connect(host, port)
          .addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
              if (!future.isSuccess()) {
                // offer to ready message queue
                offerToReadyQueue(InstantMessage.systemMessage("Channel is not connected !"));
              } else {
                offerToReadyQueue(InstantMessage.systemMessage("Channel is connected !"));
                // initialize the channel
                channel = future.channel();
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
              offerToReadyQueue(InstantMessage.systemMessage("Channel is closed !"));
            }
          })
          .sync();

      offerToReadyQueue(InstantMessage.systemMessage("Connection closed !"));
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
    channel.writeAndFlush(InstantMessage.userMessage(userId, buff));
  }

  /**
   * read from server.
   *
   * @return content
   * @throws InterruptedException if interrupted while waiting
   */
  public InstantMessage readMessage() throws InterruptedException {
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
  private boolean offerToReadyQueue(InstantMessage message) {
    return readyToReadMessages.offer(message);
  }

  public String getUserId() {
    return userId;
  }

  class ClientHandler extends SimpleChannelInboundHandler<InstantMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      // echo
      channel.writeAndFlush(InstantMessage.echoMessage(userId));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InstantMessage msg) throws Exception {
      // offer to ready message queue
      offerToReadyQueue(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
        throws Exception {
      cause.printStackTrace();
      ctx.close();
    }
  }

}
