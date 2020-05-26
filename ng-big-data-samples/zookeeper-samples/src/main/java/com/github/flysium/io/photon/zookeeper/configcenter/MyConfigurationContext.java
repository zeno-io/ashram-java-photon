package com.github.flysium.io.photon.zookeeper.configcenter;

/**
 * 上下文
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class MyConfigurationContext {

  /**
   * 是否只有当数据变化才返回配置
   */
  private boolean readOnlyChanged;

  public MyConfigurationContext(boolean readOnlyChanged) {
    this.readOnlyChanged = readOnlyChanged;
  }

  public boolean isReadOnlyChanged() {
    return readOnlyChanged;
  }

  public void setReadOnlyChanged(boolean readOnlyChanged) {
    this.readOnlyChanged = readOnlyChanged;
  }
}
