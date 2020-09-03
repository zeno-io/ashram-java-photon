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

package xyz.flysium.photon.rpc.remoting.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.rpc.container.RpcContainer;
import xyz.flysium.photon.rpc.remoting.portocol.RpcMessage;
import xyz.flysium.photon.rpc.remoting.portocol.RpcRequest;
import xyz.flysium.photon.rpc.remoting.portocol.RpcResponse;
import xyz.flysium.photon.rpc.remoting.serializer.SerializerUtils;

/**
 * server
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class NettyServer {

  private Logger logger = LoggerFactory.getLogger(getClass());
  private final RpcContainer container;
  private final int port;
  private Channel channel = null;
  private volatile boolean inited = false;
  private NioEventLoopGroup bossGroup;
  private NioEventLoopGroup workerGroup;

  public NettyServer(RpcContainer container, int port) {
    this.container = container;
    this.port = port;
  }

  public CompletableFuture<Void> start() throws InterruptedException {
    if (inited) {
      return CompletableFuture.completedFuture(null);
    }
    synchronized (this) {
      final CompletableFuture<Void> completableFuture = doStart();
      inited = true;
      return completableFuture;
    }
  }

  private CompletableFuture<Void> doStart() {
    final CompletableFuture<Void> completableFuture = new CompletableFuture<>();

    bossGroup = new NioEventLoopGroup(1);
    workerGroup = new NioEventLoopGroup(3);

    Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

    new ServerBootstrap()
        .group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .option(ChannelOption.SO_BACKLOG, 100)
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .childOption(ChannelOption.TCP_NODELAY, true)
        .childOption(ChannelOption.SO_LINGER, 100)
        .childHandler(new ChannelInitializer<SocketChannel>() {

          @Override
          protected void initChannel(SocketChannel ch) throws Exception {

            ch.pipeline().addLast(new RpcMessageDecoder())
                .addLast(new RpcMessageEncoder())
                .addLast(new SimpleChannelInboundHandler<RpcMessage>() {

                  @Override
                  protected void channelRead0(ChannelHandlerContext ctx, RpcMessage msg)
                      throws Exception {
                    receive(ctx, msg);
                  }
                });
          }
        })
        .bind(port)
        .addListener(new ChannelFutureListener() {
          @Override
          public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
              channel = future.channel();
              completableFuture.complete(null);
            }
          }
        });
    return completableFuture;
  }

  public void stop() {
    bossGroup.shutdownGracefully();
    workerGroup.shutdownGracefully();
  }

  private void send(Channel channel, RpcResponse resp)
      throws Exception {
    long mId = resp.getId();
    byte[] body = SerializerUtils.toBytes(resp);
    RpcMessage msg = new RpcMessage((short) 2, mId, body);
    channel.writeAndFlush(msg).sync();
  }

  private void receive(ChannelHandlerContext ctx, RpcMessage msg) {
    container.getExecutor().submit(() -> {
      long mId = msg.getId();
      RpcResponse resp = null;
      try {
        byte[] body = msg.getBody();
        RpcRequest req = SerializerUtils.fromBytes(body, RpcRequest.class);

        resp = container.getInvoker(req.getService()).invoke(req);
      } catch (Exception e) {
        resp = new RpcResponse(mId, e);
      } finally {
        try {
          logger.info("resp:----->" + resp.getId());
          send(ctx.channel(), resp);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

}
