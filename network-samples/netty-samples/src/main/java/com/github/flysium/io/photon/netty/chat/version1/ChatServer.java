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

package com.github.flysium.io.photon.netty.chat.version1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Chat Server
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ChatServer {

  private final int port;
  private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  private final BlockingQueue<String> console = new LinkedBlockingQueue<>(1024);

  public ChatServer(int port) {
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
            protected void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline()
                  .addLast(new ServerInboundHandler());
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
   * write to others
   */
  private void writeToOthers(ChannelHandlerContext ctx, String string) {
    channels.stream()
        .filter(channel -> !channel.id().equals(ctx.channel().id()))
        .forEach(channel -> {
          channel.writeAndFlush(Unpooled.copiedBuffer(string.getBytes()));
        });
  }

  private String newMessage(String string) {
    return LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " "
        + string;
  }

  class ServerInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      ByteBuf buf = (ByteBuf) msg;
      logger("refCnt: " + buf.refCnt());
      try {
        String readiedString = buf.toString(CharsetUtil.UTF_8);
        String responseString = newMessage(readiedString);
        logger("Received: " + responseString);
        // write to others
        writeToOthers(ctx, responseString);
      } finally {
        ReferenceCountUtil.release(buf);
        logger("refCnt: " + buf.refCnt());
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
