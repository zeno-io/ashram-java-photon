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

package com.github.flysium.io.photon.netty.c001_frame.lengthfield;

import com.github.flysium.io.photon.netty.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Length Field Server
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class LengthFieldServer {

  public static void main(String[] args) throws InterruptedException {
    LengthFieldServer server = new LengthFieldServer(Constant.PORT);
    server.start();
  }

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final int port;

  public LengthFieldServer(int port) {
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
                  .addLast(new ProtocolMessageDecoder())
                  .addLast(new ProtocolMessageEncoder())
                  .addLast(new ServerChannelChildHandler());
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

  /**
   * write to client.
   *
   * @param channel Channel
   * @param message content
   */
  protected void sendMessage(Channel channel, ProtocolMessage message) {
    channel.write(message);
  }

  /**
   * write to client.
   *
   * @param channel Channel
   * @param message content
   */
  protected void sendMessageAndFlush(Channel channel, ProtocolMessage message) {
    channel.writeAndFlush(message);
  }

  class ServerChannelChildHandler extends SimpleChannelInboundHandler<ProtocolMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolMessage msg) {
      // TODO
      logger.info("server read msg: {}", msg);

      String response = "hello from server: " +
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

      sendMessageAndFlush(ctx.channel(), new ProtocolMessage((short) 0x1, response.getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error(cause.getMessage(), cause);
      ctx.close();
    }

  }

}
