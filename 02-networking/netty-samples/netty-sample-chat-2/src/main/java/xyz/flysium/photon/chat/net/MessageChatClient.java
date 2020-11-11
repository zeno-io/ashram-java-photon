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

package xyz.flysium.photon.chat.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import xyz.flysium.photon.chat.model.InstantMessage;
/**
 * Chat Client.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class MessageChatClient {

  private final String host;
  private final int port;
  private final int pollTimeout;

  private final String userId;

  private Channel channel = null;
  private final BlockingQueue<InstantMessage> readyToReadMessages = new LinkedBlockingQueue<>(1024);

  public MessageChatClient(String host, int port, int pollTimeout, String userId) {
    this.host = host;
    this.port = port;
    this.pollTimeout = pollTimeout;
    this.userId = userId;
  }

  /**
   * start the client.
   */
  public void start() throws InterruptedException {
    EventLoopGroup workGroup = new NioEventLoopGroup(1);
    try {
      new Bootstrap()
          .group(workGroup)
          .option(ChannelOption.TCP_NODELAY, true)
          .option(ChannelOption.SO_LINGER, 100)
          .channel(NioSocketChannel.class)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline()
                  .addLast(new InstantMessageDecoder())
                  .addLast(new InstantMessageEncoder())
                  .addLast(new ClientHandler());
            }
          })
          .connect(host, port)
          .addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
              if (!future.isSuccess()) {
                // offer to ready message queue
                offerToReadyQueue(InstantMessage.systemMessage("Channel is not connected !"));
              } else {
                offerToReadyQueue(InstantMessage.systemMessage("Channel is connected !"));
                // initialize the channel
                channel = future.channel();
              }
            }
          }).sync()
          .channel().closeFuture()
          .addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
              if (future.isSuccess()) {
                System.out.println("channel close success");
              } else {
                System.out.println("channel close fail");
              }
              offerToReadyQueue(InstantMessage.systemMessage("Channel is closed !"));
            }
          })
          .sync();

      offerToReadyQueue(InstantMessage.systemMessage("Connection closed !"));
    } finally {
      workGroup.shutdownGracefully();
    }
  }

  /**
   * stop the client.
   */
  public void stop() {
    if (channel != null) {
      channel.close();
    }
  }

  /**
   * write to server.
   *
   * @param buff content
   */
  public void sendMessage(String buff) {
    channel.writeAndFlush(InstantMessage.userMessage(userId, buff));
  }

  /**
   * read from server.
   *
   * @return content
   * @throws InterruptedException if interrupted while waiting
   */
  public InstantMessage readMessage() throws InterruptedException {
    return tryPoll(readyToReadMessages);
  }

  private <T> T tryPoll(BlockingQueue<T> queue) throws InterruptedException {
    if (pollTimeout <= 0) {
      return queue.poll();
    }
    return queue.poll(pollTimeout, TimeUnit.MILLISECONDS);
  }

  /**
   * offer to ready message queue
   */
  private boolean offerToReadyQueue(InstantMessage message) {
    return readyToReadMessages.offer(message);
  }

  public String getUserId() {
    return userId;
  }

  class ClientHandler extends SimpleChannelInboundHandler<InstantMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      // echo
      channel.writeAndFlush(InstantMessage.echoMessage(userId));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InstantMessage msg) throws Exception {
      // offer to ready message queue
      offerToReadyQueue(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
        throws Exception {
      cause.printStackTrace();
      ctx.close();
    }
  }

}
