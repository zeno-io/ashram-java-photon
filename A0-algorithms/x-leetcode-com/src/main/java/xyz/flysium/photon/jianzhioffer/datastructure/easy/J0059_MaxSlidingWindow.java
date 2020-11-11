package xyz.flysium.photon.jianzhioffer.datastructure.easy;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 剑指 Offer 59 - I. 滑动窗口的最大值
 * <p>
 * https://leetcode-cn.com/problems/hua-dong-chuang-kou-de-zui-da-zhi-lcof/
 *
 * @author zeno
 */
public interface J0059_MaxSlidingWindow {

  // 给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。
  // 你可以假设 k 总是有效的，在输入数组不为空的情况下，1 ≤ k ≤ 输入数组的大小。

  // 执行用时：14 ms, 在所有 Java 提交中击败了75.64% 的用户
  class Solution {

    public int[] maxSlidingWindow(int[] nums, int k) {
      if (nums.length == 0) {
        return new int[0];
      }
      final int length = nums.length;
      int[] ans = new int[length - k + 1];
      // 队首到队尾，维护单调递减
      Deque<Integer> queue = new LinkedList<>();
      for (int i = 0; i < k; i++) { // 未形成窗口
        // 要加元素x，先从队尾删掉小于x的元素，再加入
        while (!queue.isEmpty() && queue.peekLast() < nums[i]) {
          queue.pollLast();
        }
        queue.offerLast(nums[i]);
      }
      // 队首就是第一个滑动窗口的最大值
      ans[0] = queue.peekFirst();
      for (int i = k; i < length; i++) {// 形成窗口
        // 滑动窗口要缩小，去除的元素是否对首的元素，如果是，删除
        if (queue.peekFirst() == nums[i - k]) {
          queue.pollFirst();
        }
        // 要加元素x，先从队尾删掉小于x的元素，再加入
        while (!queue.isEmpty() && queue.peekLast() < nums[i]) {
          queue.pollLast();
        }
        queue.offerLast(nums[i]);
        // 队首就是这次滑动窗口的最大值
        ans[i - k + 1] = queue.peekFirst();
      }
      return ans;
    }

  }


}
