package com.github.flysium.io.photon.netty.c000_quickstart;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

/**
 * Test <code>io.netty.channel.socket.ServerSocketChannel</code>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T04_ServerSocketChannel {

  public static void main(String[] args) throws InterruptedException {
    EventLoopGroup thread = new NioEventLoopGroup(1);

    ServerSocketChannel server = new NioServerSocketChannel();
    thread.register(server);

    //响应式
    ChannelPipeline p = server.pipeline();
    p.addLast(new ChannelInitializer<SocketChannel>() {

      @Override
      protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new ChannelInboundHandlerAdapter() {

          @Override
          public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("server registered...");
          }
        });
      }
    });  //accept接收客户端，并且注册到selector

    ChannelFuture bind = server.bind(new InetSocketAddress("127.0.0.1", 9090));

    bind.sync().channel().closeFuture().sync();
    System.out.println("server close....");
  }

}
