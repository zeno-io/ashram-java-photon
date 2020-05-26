package com.github.flysium.io.photon.zookeeper.distributedlock;

/**
 * 获得锁结果
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class PredicateLockResult {

  private final boolean getsTheLock;
  private final String pathToWatch;

  PredicateLockResult(boolean getsTheLock, String pathToWatch) {
    this.getsTheLock = getsTheLock;
    this.pathToWatch = pathToWatch;
  }

  public boolean isGetsTheLock() {
    return getsTheLock;
  }

  public String getPathToWatch() {
    return pathToWatch;
  }
}

