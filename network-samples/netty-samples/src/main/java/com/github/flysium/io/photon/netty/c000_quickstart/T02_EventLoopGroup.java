package com.github.flysium.io.photon.netty.c000_quickstart;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import java.io.IOException;

/**
 * Test <code>EventLoopGroup</code>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T02_EventLoopGroup {

  public static void main(String[] args) throws IOException {
    // group  线程池
    EventLoopGroup selector = new NioEventLoopGroup(2);
    selector.execute(() -> {
      try {
        for (; ; ) {
          System.out.println("hello world001");
          Thread.sleep(1000);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    selector.execute(() -> {
      try {
        for (; ; ) {
          System.out.println("hello world002");
          Thread.sleep(1000);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    System.in.read();
  }

}
