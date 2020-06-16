package com.github.flysium.io.sample.disruptor.trace;

/**
 * CounterTracer Factory.
 *
 * @author Sven Augustus
 */
public class CounterTracerFactory {

  public CounterTracer newInstance(boolean single, long expectedCount) {
    return single ? new SimpleTracer(expectedCount) : new SafeCountTracer(expectedCount);
  }

}
