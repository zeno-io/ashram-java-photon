package com.github.flysium.io.sample.disruptor.sample2;

import com.lmax.disruptor.RingBuffer;

/**
 * TradeTransaction Producer.
 *
 * @author Sven Augustus
 */
public class TradeTransactionProducer {

  private final RingBuffer<TradeTransaction> ringBuffer;

  public TradeTransactionProducer(RingBuffer<TradeTransaction> ringBuffer) {
    this.ringBuffer = ringBuffer;
  }

  public void onData(Object msg) {
    // Grab the next sequence
    long sequence = ringBuffer.next();
    try {
      // Get the entry in the Disruptor
      TradeTransaction event = ringBuffer.get(sequence);
      // for the sequence
      // Fill with data
      event.setMsg(msg);
    } finally {
      ringBuffer.publish(sequence);
    }
  }

}
