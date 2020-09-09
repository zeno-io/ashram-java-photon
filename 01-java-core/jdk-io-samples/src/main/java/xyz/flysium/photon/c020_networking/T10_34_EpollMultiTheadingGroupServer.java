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

package xyz.flysium.photon.c020_networking;

import java.io.IOException;
import java.net.StandardSocketOptions;
import xyz.flysium.photon.c002_nio.s07_selector_group.NIOEventLoopGroup;
import xyz.flysium.photon.c002_nio.s07_selector_group.NIOExtendSocketOptions;
import xyz.flysium.photon.c002_nio.s07_selector_group.NIOServerBootStrap;

/**
 * NIO Server with I/O multiplexing model, multi-threading， multi selector (epoll)
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T10_34_EpollMultiTheadingGroupServer {

  public static void main(String[] args) throws IOException, InterruptedException {
    NIOEventLoopGroup bossGroup = new NIOEventLoopGroup(1, "nio-boss-");
    NIOEventLoopGroup workerGroup = new NIOEventLoopGroup(3, "nio-worker-");

    NIOServerBootStrap bootstrap = new NIOServerBootStrap()
        .group(bossGroup, workerGroup)
        // FIXME 当backlog 增长为4096时，T10_0_C10KClient 的 11w连接测试可以缩短到 3秒左右完成
        .option(NIOExtendSocketOptions.SERVER_BACKLOG, T10_0_C10KClient.SERVER_BACKLOG)
        .childOption(StandardSocketOptions.SO_KEEPALIVE, true)
        .childOption(StandardSocketOptions.TCP_NODELAY, true)
        .childOption(StandardSocketOptions.SO_LINGER, 100)
        .bind(T10_0_C10KClient.SERVER_PORT);
    System.out.println(bootstrap.toString());
  }

}
