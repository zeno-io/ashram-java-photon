package com.github.flysium.io.sample.disruptor.sample2;

import com.github.flysium.io.sample.disruptor.EventCallback;
import com.lmax.disruptor.EventHandler;

/**
 * 处理增值业务的消费者
 *
 * @author Sven Augustus
 */
public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {

  private EventCallback<TradeTransaction> callback;

  public TradeTransactionVasConsumer(EventCallback<TradeTransaction> callback) {
    this.callback = callback;
  }

  @Override
  public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
    callback.callback(event);
  }

}