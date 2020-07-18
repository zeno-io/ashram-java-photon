package com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NIO Event Loop Group
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class NIOEventLoopGroup {

  protected static final Logger logger = LoggerFactory.getLogger(NIOEventLoopGroup.class);

  private NIOServerBootStrap bootStrap;
  private final NIOEventLoop[] selects;

  public NIOEventLoopGroup(int threads) {
    this(threads, "nio-");
  }

  public NIOEventLoopGroup(int threads, String namePrefix) {
    this(threads, new ThreadPerTaskExecutor(new CustomizableThreadFactory(namePrefix)));
  }

  public NIOEventLoopGroup(int threads, Executor executor) {
    selects = new NIOEventLoop[threads];
    for (int i = 0; i < threads; i++) {
      selects[i] = new NIOEventLoop(executor, new LinkedBlockingQueue<>());
    }
  }

  public NIOServerBootStrap getBootStrap() {
    return bootStrap;
  }

  /**
   * Sets the percentage of the desired amount of time spent for I/O in the child event loops.  The
   * default value is {@code 50}, which means the event loop will try to spend the same amount of
   * time for I/O as for non-I/O tasks.
   */
  public void setIoRatio(int ioRatio) {
    for (NIOEventLoop e : this.selects) {
      e.setIoRatio(ioRatio);
    }
  }

  void setBootStrap(NIOServerBootStrap bootStrap) {
    if (this.bootStrap != null) {
      throw new IllegalStateException("bootStrap set already");
    }
    this.bootStrap = bootStrap;
  }

  private final AtomicLong i = new AtomicLong(0);

  public NIOEventLoop choose() {
    // see DefaultEventExecutorChooserFactory#PowerOfTwoEventExecutorChooser
    if (isPowerOfTwo(selects.length)) {
      int index = (int) (i.getAndIncrement() & selects.length - 1);
      return selects[index];
    }
    // see DefaultEventExecutorChooserFactory#GenericEventExecutorChooser
    int index = (int) (i.getAndIncrement() % selects.length);
    return selects[index];
  }

  private static boolean isPowerOfTwo(int val) {
    return (val & -val) == val;
  }

}



