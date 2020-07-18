package com.github.flysium.io.photon.inoutput.c002_nio.s07_selector_group;

import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public final class ThreadPerTaskExecutor implements Executor {

  private final ThreadFactory threadFactory;

  public ThreadPerTaskExecutor(ThreadFactory threadFactory) {
    this.threadFactory = ObjectUtil.checkNotNull(threadFactory, "threadFactory");
  }

  @Override
  public void execute(Runnable command) {
    threadFactory.newThread(command).start();
  }
}

