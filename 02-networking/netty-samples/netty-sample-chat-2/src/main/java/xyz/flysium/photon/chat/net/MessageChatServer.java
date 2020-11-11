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

package xyz.flysium.photon.chat.net;

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
import xyz.flysium.photon.chat.model.InstantMessage;
import xyz.flysium.photon.chat.model.MessageType;

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
