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

package com.github.flysium.io.photon.netty.c000_quickstart;

import com.github.flysium.io.photon.netty.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * Hello, Netty
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class HelloServer {

  public static void main(String[] args) throws InterruptedException {
    NettyServer nettyServer = new NettyServer(Constant.PORT);
    nettyServer.start();
  }

  static class NettyServer {

    private final int port;

    NettyServer(int port) {
      this.port = port;
    }

    public void start() throws InterruptedException {
      // thread pool for handling the events
      EventLoopGroup bossGroup = new NioEventLoopGroup(1);
      EventLoopGroup workerGroup = new NioEventLoopGroup(4);

      try {
        // server's bootstrap
        new ServerBootstrap()
            .group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel channel) throws Exception {
                channel.pipeline().addLast(new ServerInboundHandler());
              }
            })
            .bind(port)
            .sync()
            // wait for close sync
            .channel().closeFuture().sync();
      } finally {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
      }
    }

    static class ServerInboundHandler extends ChannelInboundHandlerAdapter {

      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        ByteBuf buf = (ByteBuf) msg;
        try {

          // buf.toString(CharsetUtil.UTF_8); ----> AbstractByteBuf
          // ByteBufUtil.decodeString(buf, index, length, charset);
          // ByteBufUtil.decodeString(buf, buf.readerIndex(), buf.readableBytes(), CharsetUtil.UTF_8);

          // read
          String requestString = buf.toString(CharsetUtil.UTF_8);
          System.out.println("channel read：" + requestString);
          System.out.println("refCnt：" + buf.refCnt());

          // write
          String responseString = requestString + " (Received)";
          ctx.writeAndFlush(Unpooled.copiedBuffer(responseString.getBytes()));
          System.out.println("channel write：" + responseString);

          // close the Channel and notify the ChannelFuture once the operation completes.
          ctx.close();
        } finally {
          ReferenceCountUtil.release(buf);
          System.out.println("refCnt：" + buf.refCnt());
        }
      }

      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
      }
    }
  }


}
