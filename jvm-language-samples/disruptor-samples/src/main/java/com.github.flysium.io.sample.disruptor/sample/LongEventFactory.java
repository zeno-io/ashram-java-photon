package com.github.flysium.io.sample.disruptor.sample;

import com.lmax.disruptor.EventFactory;

/**
 * Event Factory.
 *
 * @author Sven Augustus
 */
public class LongEventFactory implements EventFactory<LongEvent> {

  @Override
  public LongEvent newInstance() {
    return new LongEvent();
  }

}