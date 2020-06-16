package com.github.flysium.io.sample.disruptor.sample;

import com.github.flysium.io.sample.disruptor.EventCallback;
import com.lmax.disruptor.WorkHandler;

/**
 * Consumer.
 *
 * @author Sven Augustus
 */
public class LongEventWorkHandler implements WorkHandler<LongEvent> {

  private EventCallback callback;

  public LongEventWorkHandler(EventCallback callback) {
    this.callback = callback;
  }

  @Override
  public void onEvent(LongEvent event) throws Exception {
    callback.callback(event);
    // clearing Objects From the Ring Buffer
    event.clear();
  }

}