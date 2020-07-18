package com.github.flysium.io.photon.inoutput.c020_networking;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * netty
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T10_35_NettyServer {

  protected static final Logger logger = LoggerFactory.getLogger(T10_35_NettyServer.class);

  public static void main(String[] args) throws InterruptedException {
    // thread pool for handling the events
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup(3);

    try {
      // server's bootstrap
      new ServerBootstrap()
          .group(bossGroup, workerGroup)
          .option(ChannelOption.SO_BACKLOG, T10_0_C10KClient.SERVER_BACKLOG)
          .childOption(ChannelOption.SO_KEEPALIVE, true)
          .childOption(ChannelOption.TCP_NODELAY, true)
          .childOption(ChannelOption.SO_LINGER, 100)
          .channel(NioServerSocketChannel.class)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
              channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                @Override
                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                  ByteBuf buf = (ByteBuf) msg;
                  try {
                    // read
                    String requestString = buf.toString(CharsetUtil.UTF_8);

                    if (logger.isDebugEnabled()) {
                      logger.debug("readied from client: " + ctx.channel().remoteAddress()
                          + ", data: " + requestString);
                    }

                    // FIXME 模拟业务逻辑处理时间耗时  应该使用业务线程池
                    try {
                      TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                      logger.error(e.getMessage(), e);
                    }

                    // write
                    String responseString = "recv->" + requestString;

                    if (logger.isDebugEnabled()) {
                      logger.debug(
                          "ready write to client: " + ctx.channel().remoteAddress());
                    }

                    ctx.writeAndFlush(Unpooled.copiedBuffer(responseString.getBytes()));

                    // close the Channel and notify the ChannelFuture once the operation completes.
                    // TODO 这里读写一次就关闭连接了, 注释掉则不由服务器主动关闭
                    ctx.close();

                  } finally {
                    ReferenceCountUtil.release(buf);
                  }
                }

                @Override
                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                    throws Exception {
                  logger.error(cause.getMessage(), cause);
                  ctx.close();
                }
              });
            }
          })
          .bind(T10_0_C10KClient.SERVER_PORT)
          .sync()
          // wait for close sync
          .channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

}
