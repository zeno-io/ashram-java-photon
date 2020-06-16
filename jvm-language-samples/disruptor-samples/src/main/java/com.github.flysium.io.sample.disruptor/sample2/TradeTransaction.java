package com.github.flysium.io.sample.disruptor.sample2;

/**
 * Trade Transaction.
 *
 * @author Sven Augustus
 */
public class TradeTransaction {

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
