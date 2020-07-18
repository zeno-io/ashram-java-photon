package com.github.flysium.io.photon.inoutput.c020_networking;

import com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group.NIOEventLoopGroup;
import com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group.NIOExtendSocketOptions;
import com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group.NIOServerBootStrap;
import java.io.IOException;
import java.net.StandardSocketOptions;

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
