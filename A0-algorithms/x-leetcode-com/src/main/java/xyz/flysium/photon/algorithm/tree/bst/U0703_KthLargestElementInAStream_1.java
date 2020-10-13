package xyz.flysium.photon.algorithm.tree.bst;

import java.util.PriorityQueue;

/**
 * 703. 数据流中的第K大元素
 * <p>
 * https://leetcode-cn.com/problems/kth-largest-element-in-a-stream/
 *
 * @author zeno
 */
public interface U0703_KthLargestElementInAStream_1 {

  // 设计一个找到数据流中第K大元素的类（class）。
  // 注意是排序后的第K大元素，不是第K个不同的元素。
  //
  // 你的 KthLargest 类需要一个同时接收整数 k 和整数数组nums 的构造器，它包含数据流中的初始元素。
  // 每次调用 KthLargest.add，返回当前数据流中第K大的元素。
  //

  //  你可以假设 nums 的长度≥ k-1 且k ≥ 1。

  // 17 ms
  class KthLargest {

    private final int k;
    private final PriorityQueue<Integer> minHeap;

    public KthLargest(int k, int[] nums) {
      this.k = k;
      this.minHeap = new PriorityQueue<Integer>(k);
      for (int num : nums) {
        add(num);
      }
    }

    public int add(int val) {
      if (minHeap.size() < k) {
        minHeap.add(val);
      } else if (val > minHeap.peek()) {
        minHeap.poll();
        minHeap.add(val);
      }
      return minHeap.peek();
    }

  }

/**
 * Your KthLargest object will be instantiated and called as such: KthLargest obj = new
 * KthLargest(k, nums); int param_1 = obj.add(val);
 */

}
