package com.github.flysium.io.photon.zookeeper.configcenter;

import java.util.Objects;

/**
 * 配置信息
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class MyConfiguration {

  // TODO
  private String conf;

  public MyConfiguration() {
  }

  public MyConfiguration(String conf) {
    this.conf = conf;
  }

  public String getConf() {
    return conf;
  }

  public void setConf(String conf) {
    this.conf = conf;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MyConfiguration that = (MyConfiguration) o;
    return Objects.equals(conf, that.conf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conf);
  }

}
