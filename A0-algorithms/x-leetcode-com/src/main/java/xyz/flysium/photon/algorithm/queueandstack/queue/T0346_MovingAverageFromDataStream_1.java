package xyz.flysium.photon.algorithm.queueandstack.queue;

/**
 * 346. 数据流中的移动平均值
 * <p>
 * https://leetcode-cn.com/problems/moving-average-from-data-stream/
 *
 * @author zeno
 */
public interface T0346_MovingAverageFromDataStream_1 {

  // 给定一个整数数据流和一个窗口大小，根据该滑动窗口的大小，计算其所有整数的移动平均值。

  // 53 ms
  class MovingAverage {

    private final int[] arr;
    private final int capacity;
    private int size = 0;
    private int toBeAdded = 0;
    private int sum = 0;

    /**
     * Initialize your data structure here.
     */
    public MovingAverage(int size) {
      arr = new int[size];
      capacity = size;
    }

    public double next(int val) {
      // compute the summary
      sum += val;
      if (size >= capacity) {
        sum -= arr[toBeAdded];
      }
      // add
      arr[toBeAdded] = val;
      toBeAdded++;
      if (toBeAdded >= capacity) {
        toBeAdded = 0;
      }
      if (size < capacity) {
        size++;
      }

      return (size == 0) ? 0 : sum * 1.0 / size;
    }

  }

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */

}
