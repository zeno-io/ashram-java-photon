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

package com.github.flysium.io.photon.netty;

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

/**
 * Netty Client.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class Client {

  public static void main(String[] args) throws InterruptedException {
    NettyClient nettyClient = new NettyClient("127.0.0.1", 8090);
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
