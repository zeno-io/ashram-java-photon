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

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.Constant;

/**
 * Delimiter Client
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class DelimiterClient {

  public static void main(String[] args) throws InterruptedException {
    DelimiterClient client = new DelimiterClient(Constant.HOST, Constant.PORT,
        Constant.DELIMITER);
    client.start();
  }

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final String host;
  private final int port;
  private final String delimiter;
  private Channel channel = null;

  public DelimiterClient(String host, int port, String delimiter) {
    this.host = host;
    this.port = port;
    this.delimiter = delimiter;
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
            protected void initChannel(SocketChannel ch) {
              ch.pipeline()
                  .addLast(new DelimiterBasedFrameDecoder(1024,
                      Unpooled.copiedBuffer(delimiter.getBytes())))
                  .addLast(new StringDecoder())
                  .addLast(new StringEncoder())
                  .addLast(new ClientChannelHandler());
            }
          })
          .connect(host, port)
          .addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
              channel = future.channel();
            }
          }).sync()
          .channel().closeFuture()
          .sync();

      logger.warn("Connection closed !");
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
   * @param message content
   */
  protected void sendMessage(String message) {
    channel.write(message + delimiter);
  }

  /**
   * write to server.
   *
   * @param message content
   */
  protected void sendMessageAndFlush(String message) {
    channel.writeAndFlush(message + delimiter);
  }

  class ClientChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
      // TODO
      String msg = "hello from client: " +
          LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

      sendMessage(msg);

//      sendMessage(Constant.BIG_STRING_2048);
      sendMessageAndFlush(Constant.BIG_STRING_1024);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      String body = (String) msg;
      logger.info("client read msg: {}", body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error(cause.getMessage(), cause);
      ctx.close();
    }
  }

}
