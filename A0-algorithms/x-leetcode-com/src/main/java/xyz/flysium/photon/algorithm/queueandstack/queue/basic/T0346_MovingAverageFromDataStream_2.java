package xyz.flysium.photon.algorithm.queueandstack.queue.basic;

/**
 * 346. 数据流中的移动平均值
 * <p>
 * https://leetcode-cn.com/problems/moving-average-from-data-stream/
 *
 * @author zeno
 */
public interface T0346_MovingAverageFromDataStream_2 {

  // 给定一个整数数据流和一个窗口大小，根据该滑动窗口的大小，计算其所有整数的移动平均值。
  // 65 ms
  class MovingAverage {

    private final int[] arr;
    private int head = -1;
    private int tail = -1;
    private int size = 0;

    /**
     * Initialize your data structure here.
     */
    public MovingAverage(int size) {
      arr = new int[size];
    }

    public double next(int val) {
      if (isFull()) {
        dequeue();
      }
      enqueue(val);

      double sum = 0;
      if (size > 0) {
        int cnt = 0;
        int i = head;
        while (cnt < size) {
          sum += arr[i];
          i++;
          if (i >= arr.length) {
            i = 0;
          }
          cnt++;
        }
      }

      return (size == 0) ? 0 : sum / size;
    }

    private boolean dequeue() {
      if (isEmpty()) {
        return false;
      }
      if (head == tail) {
        head = -1;
        tail = -1;
        size = 0;
        return true;
      }
      head++;
      if (head >= arr.length) {
        head = 0;
      }
      size--;
      return true;
    }

    private boolean enqueue(int val) {
      if (isFull()) {
        return false;
      }
      if (isEmpty()) {
        head = 0;
      }
      tail++;
      if (tail >= arr.length) {
        tail = 0;
      }
      arr[tail] = val;
      size++;
      return true;
    }

    private boolean isEmpty() {
      return size == 0;
    }

    private boolean isFull() {
      return size == arr.length;
    }

  }

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */

}
