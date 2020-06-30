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
import com.github.flysium.io.photon.netty.samples.chat.version2.model.MessageType;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Chat Server
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class MessageChatServer {

  private final int port;
  private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  private final BlockingQueue<String> console = new LinkedBlockingQueue<>(1024);

  // consumer for when echo message arrived
  private final BiConsumer<String, String> echoChannelConsumer;
  // consumer for when channel inactive
  private final Consumer<String> channelInactiveConsumer;

  public MessageChatServer(int port) {
    this(port, null, null);
  }

  public MessageChatServer(int port, BiConsumer<String, String> echoChannelConsumer,
      Consumer<String> channelInactiveConsumer) {
    this.port = port;
    this.echoChannelConsumer = echoChannelConsumer;
    this.channelInactiveConsumer = channelInactiveConsumer;
  }

  /**
   * Start the server.
   */
  public void start() throws InterruptedException {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workGroup = new NioEventLoopGroup(12);
    try {
      new ServerBootstrap()
          .group(bossGroup, workGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1000)
          .childOption(ChannelOption.SO_KEEPALIVE, true)
          .childOption(ChannelOption.TCP_NODELAY, true)
          .childOption(ChannelOption.SO_LINGER, 100)
          .childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) {
              ch.pipeline()
                  .addLast(new InstantMessageDecoder())
                  .addLast(new InstantMessageEncoder())
                  .addLast(new ServerChildHandler());
            }
          })
          .bind(port)
          .sync()
          .channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }
  }

  private void logger(String s) {
    console.offer(s);
  }

  /**
   * read logger.
   *
   * @return content
   * @throws InterruptedException if interrupted while waiting
   */
  public String readConsole() throws InterruptedException {
    return console.poll(10, TimeUnit.MILLISECONDS);
  }

  /**
   * write to other channels except this
   */
  public void writeToOthers(String channelId, InstantMessage message) {
    channels.stream()
        .filter(channel -> channelId != null && !channelId.equals(getChannelId(channel)))
        .forEach(channel -> channel.writeAndFlush(message));
  }

  private String getChannelId(Channel channel) {
    return channel.id().asLongText();
  }

  class ServerChildHandler extends SimpleChannelInboundHandler<InstantMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InstantMessage msg) {
      logger("Received: " + msg.toString());
      // write to others
      if (MessageType.ECHO.equals(msg.ofType())) {
        String userId = msg.getUserId();
        if (echoChannelConsumer != null) {
          echoChannelConsumer.accept(getChannelId(ctx.channel()), userId);
        }
      } else {
        writeToOthers(getChannelId(ctx.channel()), msg);
      }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
      String channelId = getChannelId(ctx.channel());
      if (channelInactiveConsumer != null) {
        channelInactiveConsumer.accept(channelId);
      }
      channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
      channels.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      channels.remove(ctx.channel());
      logger(cause.getLocalizedMessage());
      cause.printStackTrace();
      ctx.close();
    }
  }

}
