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

package xyz.flysium.photon.c002_pojo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.Constant;

/**
 * Object Codec Client
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ObjectCodecClient {

  public static void main(String[] args) throws InterruptedException {
    ObjectCodecClient client = new ObjectCodecClient(Constant.HOST, Constant.PORT);
    client.start();
  }

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final String host;
  private final int port;
  private Channel channel = null;

  public ObjectCodecClient(String host, int port) {
    this.host = host;
    this.port = port;
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
                  .addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                  .addLast(new ObjectEncoder())
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
  protected void sendMessage(Object message) {
    channel.write(message);
  }

  /**
   * write to server.
   *
   * @param message content
   */
  protected void sendMessageAndFlush(Object message) {
    channel.writeAndFlush(message);
  }

  class ClientChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
      // TODO
      ObjectMessage msg = new ObjectMessage(1, "client", "hello " + LocalDateTime.now()
          .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
      sendMessage(msg);

      sendMessageAndFlush(new ObjectMessage(2, "client", Constant.BIG_STRING_2048));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      // TODO
      ObjectMessage body = (ObjectMessage) msg;
      logger.info("client read msg: {}", body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      logger.error(cause.getMessage(), cause);
      ctx.close();
    }
  }

}
