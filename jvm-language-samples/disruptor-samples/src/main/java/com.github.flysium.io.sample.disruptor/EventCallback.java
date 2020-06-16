package com.github.flysium.io.sample.disruptor;

/**
 * Event Callback, for Test only.
 *
 * @author Sven Augustus
 */
public interface EventCallback<T> {

  /**
   * callback
   *
   * @param event event
   */
  void callback(T event);
}
