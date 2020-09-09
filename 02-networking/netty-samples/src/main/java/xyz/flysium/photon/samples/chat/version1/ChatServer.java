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

package xyz.flysium.photon.samples.chat.version1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
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
          .option(ChannelOption.SO_BACKLOG, 1000)
          .childOption(ChannelOption.SO_KEEPALIVE, true)
          .childOption(ChannelOption.TCP_NODELAY, true)
          .childOption(ChannelOption.SO_LINGER, 100)
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
