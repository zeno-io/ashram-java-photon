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

package xyz.flysium.photon.rpc.remoting.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.rpc.remoting.portocol.RpcMessage;
import xyz.flysium.photon.rpc.remoting.portocol.RpcRequest;
import xyz.flysium.photon.rpc.remoting.portocol.RpcResponse;
import xyz.flysium.photon.rpc.remoting.serializer.SerializerUtils;

/**
 * client
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class NettyClient {

  private Logger logger = LoggerFactory.getLogger(getClass());
  private final String host;
  private final int port;
  private Channel channel = null;
  private volatile boolean inited = false;
  private NioEventLoopGroup workerGroup;

  public NettyClient(String host, int port) {
    this.host = host;
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

    workerGroup = new NioEventLoopGroup(3);

    Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

    new Bootstrap()
        .group(workerGroup)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, true)
        .option(ChannelOption.SO_LINGER, 100)
        .handler(new ChannelInitializer<SocketChannel>() {

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
        }).connect(host, port)
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
    channel.close();
    workerGroup.shutdownGracefully();
  }

  public boolean isActive() {
    return channel != null && channel.isActive();
  }

  private static final Map<Long, CompletableFuture<RpcResponse>> FUTURES = new ConcurrentHashMap<>();

  public CompletableFuture<RpcResponse> send(RpcRequest req) throws Exception {
    final long mId = req.getId();
    CompletableFuture<RpcResponse> future = new CompletableFuture<>();
    FUTURES.putIfAbsent(mId, future);
    try {
      byte[] body = SerializerUtils.toBytes(req);
      RpcMessage msg = new RpcMessage((short) 1, mId, body);
      channel.writeAndFlush(msg).sync();
    } catch (Exception e) {
      FUTURES.remove(mId);
      throw e;
    }
    return future;
  }

  private void receive(ChannelHandlerContext ctx, RpcMessage msg) throws Exception {
    long mId = msg.getId();
    byte[] body = msg.getBody();
    RpcResponse resp = SerializerUtils.fromBytes(body, RpcResponse.class);
    CompletableFuture<RpcResponse> future = FUTURES.remove(mId);
    if (future == null) {
      logger.error("err ---->" + mId);
    }
    future.complete(resp);
  }

}
