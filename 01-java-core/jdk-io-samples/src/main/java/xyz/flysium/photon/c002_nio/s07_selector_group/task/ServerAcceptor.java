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

package xyz.flysium.photon.c002_nio.s07_selector_group.task;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import xyz.flysium.photon.c002_nio.s07_selector_group.NIOChannelUtil;
import xyz.flysium.photon.c002_nio.s07_selector_group.NIOEventLoop;
import xyz.flysium.photon.c002_nio.s07_selector_group.NIOEventLoopGroup;

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
