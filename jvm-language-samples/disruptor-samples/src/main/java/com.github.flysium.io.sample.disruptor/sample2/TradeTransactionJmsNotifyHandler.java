package com.github.flysium.io.sample.disruptor.sample2;

import com.github.flysium.io.sample.disruptor.EventCallback;
import com.lmax.disruptor.EventHandler;

/**
 * Jms Sender.
 *
 * @author Sven Augustus
 */
public class TradeTransactionJmsNotifyHandler implements EventHandler<TradeTransaction> {

  private EventCallback<TradeTransaction> callback;

  public TradeTransactionJmsNotifyHandler(EventCallback<TradeTransaction> callback) {
    this.callback = callback;
  }

  @Override
  public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
    callback.callback(event);
    // clearing Objects From the Ring Buffer
    event.clear();
  }

}
