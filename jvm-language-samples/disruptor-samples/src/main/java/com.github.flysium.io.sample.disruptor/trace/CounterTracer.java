package com.github.flysium.io.sample.disruptor.trace;

/**
 * 测试结果跟踪器
 *
 * @author Sven Augustus
 */
public interface CounterTracer {

  /**
   * 开始
   */
  void start();

  /**
   * 时间差
   *
   * @return 时间差
   */
  long getMilliTimeSpan();

  /**
   * 计数
   *
   * @return 是否结束
   */
  boolean count();

  /**
   * 等待结束
   *
   * @throws InterruptedException 中断异常
   */
  void waitForReached() throws InterruptedException;
}
