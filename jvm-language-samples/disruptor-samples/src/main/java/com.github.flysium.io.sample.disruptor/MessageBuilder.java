package com.github.flysium.io.sample.disruptor;

/**
 * 消息构造器, for Test only.
 *
 * @author Sven Augustus
 */
public interface MessageBuilder {

  /**
   * 构建消息
   *
   * @param index 索引
   * @return 消息
   */
  Object build(int index);

}
