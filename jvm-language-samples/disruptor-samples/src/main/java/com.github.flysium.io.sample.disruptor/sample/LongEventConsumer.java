package com.github.flysium.io.sample.disruptor.sample;

import com.github.flysium.io.sample.disruptor.EventCallback;
import com.lmax.disruptor.EventHandler;

/**
 * Consumer.
 *
 * @author Sven Augustus
 */
public class LongEventConsumer implements EventHandler<LongEvent> {

  private EventCallback<LongEvent> callback;

  public LongEventConsumer(EventCallback<LongEvent> callback) {
    this.callback = callback;
  }

  @Override
  public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
    callback.callback(event);
    // clearing Objects From the Ring Buffer
    event.clear();
  }

}