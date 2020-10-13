package xyz.flysium.photon.algorithm.queueandstack.queue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 346. 数据流中的移动平均值
 * <p>
 * https://leetcode-cn.com/problems/moving-average-from-data-stream/
 *
 * @author zeno
 */
public interface T0346_MovingAverageFromDataStream {

  // 给定一个整数数据流和一个窗口大小，根据该滑动窗口的大小，计算其所有整数的移动平均值。

  // 53 ms
  class MovingAverage {

    private final Deque<Integer> q;
    private final int capacity;
    private int sum = 0;

    /**
     * Initialize your data structure here.
     */
    public MovingAverage(int size) {
      q = new LinkedList<>();
      capacity = size;
    }

    public double next(int val) {
      if (q.size() >= capacity) {
        sum -= q.pollFirst();
      }
      q.offerLast(val);
      sum += val;

      return (q.size() == 0) ? 0 : sum * 1.0 / q.size();
    }

  }

}
