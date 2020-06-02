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

package com.github.flysium.io.photon.netty.chat.version2.net;

import com.github.flysium.io.photon.netty.chat.version2.model.InstantMessage;
import com.github.flysium.io.photon.netty.chat.version2.model.MessageType;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Chat Server
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class MessageChatServer {

  private final int port;
  private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  private final Map<String, String> channel2UserId = new ConcurrentHashMap<>();

  private final BlockingQueue<String> console = new LinkedBlockingQueue<>(1024);

  public MessageChatServer(int port) {
    this.port = port;
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
  private void writeToOthers(ChannelHandlerContext ctx, InstantMessage message) {
    channels.stream()
        .filter(channel -> !channel.id().equals(ctx.channel().id()))
        .forEach(channel -> channel.writeAndFlush(message));
  }

  private String getChannelId(ChannelHandlerContext ctx) {
    return ctx.channel().id().asLongText();
  }

  class ServerChildHandler extends SimpleChannelInboundHandler<InstantMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InstantMessage msg) throws Exception {
      logger("Received: " + msg.toString());
      // write to others
      if (MessageType.ECHO.equals(msg.ofType())) {
        String userId = msg.getUserId();
        channel2UserId.putIfAbsent(getChannelId(ctx), userId);
        writeToOthers(ctx, InstantMessage.serverMessage(userId + " join."));
      } else {
        writeToOthers(ctx, msg);
      }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      String channelId = getChannelId(ctx);
      String userId = channel2UserId.get(channelId);
      if (userId != null) {
        writeToOthers(ctx, InstantMessage.serverMessage(userId + " leave."));
        channel2UserId.remove(channelId);
        channels.remove(ctx.channel());
      }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      channels.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
        throws Exception {
      channels.remove(ctx.channel());
      logger(cause.getLocalizedMessage());
      cause.printStackTrace();
      ctx.close();
    }
  }


}