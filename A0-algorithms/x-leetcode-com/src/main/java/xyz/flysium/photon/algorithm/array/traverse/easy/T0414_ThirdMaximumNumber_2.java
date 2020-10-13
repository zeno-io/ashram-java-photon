package xyz.flysium.photon.algorithm.array.traverse.easy;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 414. 第三大的数
 * <p>
 * https://leetcode-cn.com/problems/third-maximum-number/
 *
 * @author zeno
 */
public class T0414_ThirdMaximumNumber_2 {

  static class Solution {

    public int thirdMax(int[] nums) {
      // max heap
      PriorityQueue<Integer> heap = new PriorityQueue<>(nums.length + 1, new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
          return o1 > o2 ? -1 : (o1.intValue() == o2.intValue()) ? 0 : 1;
        }
      });
      int index = 0;
      while (index < nums.length) {
        if (!heap.contains(nums[index])) {
          heap.offer(nums[index]);
        }
        index++;
      }
      if (heap.size() >= 3) {
        heap.poll();
        heap.poll();
        return heap.poll();
      }

      return heap.poll();
    }

  }

}
