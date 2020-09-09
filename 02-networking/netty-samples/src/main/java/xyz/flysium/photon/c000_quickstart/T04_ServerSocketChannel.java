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
