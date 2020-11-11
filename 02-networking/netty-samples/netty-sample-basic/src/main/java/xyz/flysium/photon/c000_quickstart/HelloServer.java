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
import xyz.flysium.photon.Constant;

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
