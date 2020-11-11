package xyz.flysium.photon.algorithm.hash.basic.beginner;

import java.util.HashMap;
import java.util.Map;

/**
 * 359. 日志速率限制器
 * <p>
 * https://leetcode-cn.com/problems/logger-rate-limiter/
 *
 * @author zeno
 */
public interface T0359_LoggerRateLimiter {

  //请你设计一个日志系统，可以流式接收日志以及它的时间戳。
  //
  //该日志会被打印出来，需要满足一个条件：当且仅当日志内容 在过去的 10 秒钟内没有被打印过。
  //
  //给你一条日志的内容和它的时间戳（粒度为秒级），如果这条日志在给定的时间戳应该被打印出来，则返回 true，否则请返回 false。
  //
  //要注意的是，可能会有多条日志在同一时间被系统接收。
  //

  // 38 ms 84.26%
  class Logger {

    private final Map<String, Integer> hash;

    /**
     * Initialize your data structure here.
     */
    public Logger() {
      hash = new HashMap<>();
    }

    /**
     * Returns true if the message should be printed in the given timestamp, otherwise returns
     * false. If this method returns false, the message will not be printed. The timestamp is in
     * seconds granularity.
     */
    public boolean shouldPrintMessage(int timestamp, String message) {
      Integer previousTimeStamp = hash.get(message);
      if (previousTimeStamp == null ||
        ((previousTimeStamp + 10) <= timestamp)) {
        hash.put(message, timestamp);
        return true;
      }
      return false;
    }

  }

/**
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */

}
