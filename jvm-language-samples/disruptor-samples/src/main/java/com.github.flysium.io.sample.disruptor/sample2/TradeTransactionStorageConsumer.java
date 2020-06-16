package com.github.flysium.io.sample.disruptor.sample2;

import com.github.flysium.io.sample.disruptor.EventCallback;
import com.lmax.disruptor.EventHandler;

/**
 * 数据存储的消费者
 *
 * @author Sven Augustus
 */
public class TradeTransactionStorageConsumer implements EventHandler<TradeTransaction> {

  private EventCallback<TradeTransaction> callback;

  public TradeTransactionStorageConsumer(EventCallback<TradeTransaction> callback) {
    this.callback = callback;
  }

  @Override
  public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
    callback.callback(event);
  }

}