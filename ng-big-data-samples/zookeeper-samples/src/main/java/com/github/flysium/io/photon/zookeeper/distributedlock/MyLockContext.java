package com.github.flysium.io.photon.zookeeper.distributedlock;

/**
 * 上下文
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class MyLockContext {

  /**
   * 当前持有的线程，仅作为日志记录使用
   */
  private Thread currentHoldThread = null;

  public MyLockContext() {
  }

  public MyLockContext(Thread thread) {
    this.currentHoldThread = thread;
  }

  public Thread getCurrentHoldThread() {
    return currentHoldThread;
  }

  public String getCurrentHoldThreadName() {
    if (currentHoldThread == null) {
      return "Unknown";
    }
    return currentHoldThread.getName();
  }

  public void setCurrentHoldThread(Thread currentHoldThread) {
    this.currentHoldThread = currentHoldThread;
  }

}
