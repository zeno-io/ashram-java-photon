package com.github.flysium.io.sample.disruptor.sample;

import com.lmax.disruptor.RingBuffer;

/**
 * Producer.
 *
 * @author Sven Augustus
 */
public class LongEventProducer {

  private final RingBuffer<LongEvent> ringBuffer;

  public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
    this.ringBuffer = ringBuffer;
  }

  public void onData(Object msg) {
    // Grab the next sequence
    long sequence = ringBuffer.next();
    try {
      // Get the entry in the Disruptor
      LongEvent event = ringBuffer.get(sequence);
      // for the sequence
      // Fill with data
      event.setMsg(msg);
    } finally {
      ringBuffer.publish(sequence);
    }
  }

}