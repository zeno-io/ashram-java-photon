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

package xyz.flysium.photon.c001_frame.delimiter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.Constant;

/**
 * Delimiter Server
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class DelimiterServer {

  public static void main(String[] args) throws InterruptedException {
    DelimiterServer server = new DelimiterServer(Constant.PORT,
        Constant.DELIMITER);
    server.start();
  }

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final int port;
  private final String delimiter;

  public DelimiterServer(int port, String delimiter) {
    this.port = port;
    this.delimiter = delimiter;
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
                  .addLast(new DelimiterBasedFrameDecoder(1024,
                      Unpooled.copiedBuffer(delimiter.getBytes())))
                  .addLast(new StringDecoder())
                  .addLast(new StringEncoder())
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
  protected void sendMessage(Channel channel, String message) {
    channel.write(message + delimiter);
  }

  /**
   * write to client.
   *
   * @param channel Channel
   * @param message content
   */
  protected void sendMessageAndFlush(Channel channel, String message) {
    channel.writeAndFlush(message + delimiter);
  }

  class ServerChannelChildHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      // TODO
      String body = (String) msg;
      logger.info("server read msg: {}", body);

      String response = "hello from server: " +
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

      sendMessageAndFlush(ctx.channel(), response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error(cause.getMessage(), cause);
      ctx.close();
    }
  }

}
