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

package xyz.flysium.photon.c000_quickstart;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import java.util.concurrent.TimeUnit;
import xyz.flysium.photon.Constant;

/**
 * Netty Client.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class HelloClient {

  public static void main(String[] args) throws InterruptedException {
    NettyClient nettyClient = new NettyClient(Constant.HOST, Constant.PORT);
    nettyClient.start();
  }

  static class NettyClient {

    private final String host;
    private final int port;

    NettyClient(String host, int port) {
      this.host = host;
      this.port = port;
    }

    public void start() throws InterruptedException {
      // thread pool for handling the events
      EventLoopGroup workerGroup = new NioEventLoopGroup(1);
      try {

        new Bootstrap().group(workerGroup)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel channel) throws Exception {
                System.out.println("channel initialized !");
                channel.pipeline().addLast(new ClientInboundHandler());
              }
            })
            .connect(host, port)
            .addListener(new ChannelFutureListener() {
              @Override
              public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                  System.out.println("channel connect success");
                } else {
                  System.out.println("channel connect fail");
                }
              }
            })
            .sync()
            // wait for close sync
            .channel().closeFuture()
            .addListener(new ChannelFutureListener() {
              @Override
              public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                  System.out.println("channel close success");
                } else {
                  System.out.println("channel close fail");
                }
              }
            })
            .sync();

      } finally {
        workerGroup.shutdownGracefully();
      }
      System.out.println("end !");
    }

    static class ClientInboundHandler extends ChannelInboundHandlerAdapter {

      @Override
      public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        System.out.println("channel is activated !");

        // write
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Netty".getBytes()))
            //      ctx.writeAndFlush("Hello Netty")
            .addListener(new ChannelFutureListener() {

              @Override
              public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                  System.out.println("channel write success");
                } else {
                  System.out.println("channel write fail");
                }
              }
            });

//        TimeUnit.SECONDS.sleep(3);
      }

      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf buf = (ByteBuf) msg;
        try {
          // read
          String readiedString = buf.toString(CharsetUtil.UTF_8);
          System.out.println("channel read： " + readiedString);

          TimeUnit.SECONDS.sleep(3);

          // close the Channel and notify the ChannelFuture once the operation completes.
//          ctx.close();
        } finally {
          ReferenceCountUtil.release(buf);
        }
      }
    }

  }

}
