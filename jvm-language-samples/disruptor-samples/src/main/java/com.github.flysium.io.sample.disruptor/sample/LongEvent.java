package com.github.flysium.io.sample.disruptor.sample;

/**
 * Long Event.
 *
 * @author Sven Augustus
 */
public class LongEvent {

  private Object msg;

  public void clear() {
    msg = null;
  }

  public Object getMsg() {
    return msg;
  }

  public void setMsg(Object msg) {
    this.msg = msg;
  }
}
