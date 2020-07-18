package com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group.task;

import com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group.NIOChannelUtil;
import com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group.NIOEventLoop;
import com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group.NIOEventLoopGroup;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Acceptor
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class ServerAcceptor implements NIOTask {

  private final NIOEventLoopGroup group;
  private final ServerSocketChannel server;

  public ServerAcceptor(
      NIOEventLoopGroup group, ServerSocketChannel server) {
    this.group = group;
    this.server = server;
  }

  @Override
  public void run() {
    try {
      SocketChannel client = server.accept();
      if (logger.isDebugEnabled()) {
        logger.debug("accept new client：" + NIOChannelUtil.getRemoteAddress(client));
      }

      NIOEventLoop loop = group.choose();
      loop.execute(() -> {
        try {
          // 设置非阻塞模式
          client.configureBlocking(false);
          // 设置TCP Socket参数
          group.getBootStrap().getChildOptions().forEach((option, value) -> {
            try {
              client.setOption(option, value);
            } catch (UnsupportedOperationException ignore) {
              // TODO ignore
            } catch (IOException e) {
              logger.warn(e.getMessage(), e);
            }
          });
          // 注册读操作 , 以进行下一步的读操作
          final ChannelReadWriteTask readerTask = new ChannelReadWriteTask(client);
          SelectionKey key = client.register(loop.unwrappedSelector(),
              SelectionKey.OP_READ, readerTask);
          readerTask.setSelectionKey(key);

        } catch (ClosedChannelException e) {
          NIOChannelUtil.closeClient(client);
        } catch (IOException e) {
          logger.error(e.getMessage(), e);
        }
      });
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

}
