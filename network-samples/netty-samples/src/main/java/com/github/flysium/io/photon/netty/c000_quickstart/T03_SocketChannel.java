package com.github.flysium.io.photon.netty.c000_quickstart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;

/**
 * Test <code>io.netty.channel.socket.SocketChannel</code>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T03_SocketChannel {

  public static void main(String[] args) throws InterruptedException {
    EventLoopGroup thread = new NioEventLoopGroup(1);

    //客户端模式：
    SocketChannel client = new NioSocketChannel();

    thread.register(client);  //epoll_ctl(5,ADD,3)

    //响应式：
    ChannelPipeline p = client.pipeline();
    p.addLast(new ChannelInboundHandlerAdapter() {

      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
//        CharSequence str = buf.readCharSequence(buf.readableBytes(), CharsetUtil.UTF_8);
        CharSequence str = buf.getCharSequence(0, buf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println(str);
        ctx.writeAndFlush(buf);
      }
    });

    // reactor  异步的特征
    ChannelFuture connect = client.connect(new InetSocketAddress("127.0.0.1", 9090));
    ChannelFuture sync = connect.sync();

    ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
    ChannelFuture send = client.writeAndFlush(buf);
    send.sync();

    sync.channel().closeFuture().sync();

    System.out.println("client over....");
  }

}
