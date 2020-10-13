package xyz.flysium.photon.algorithm.array.basic;

/**
 * 561. 数组拆分 I
 * <p>
 * https://leetcode-cn.com/problems/array-partition-i/
 *
 * @author zeno
 */
public interface U0561_ArrayPartitionI {

  // 给定长度为 2n的数组, 你的任务是将这些数分成 n 对, 例如 (a1, b1), (a2, b2), ..., (an, bn) ，使得从1 到n 的 min(ai, bi) 总和最大。
  class Solution {

    public int arrayPairSum(int[] nums) {
      final int n = nums.length >> 1;
      // Arrays.sort(nums);
      // Counting Sort
      int minValue = -10000;
      int maxValue = 10000;
      int[] buckets = new int[maxValue - minValue + 1];
      for (int num : nums) {
        buckets[num - minValue]++;
      }
      int sum = 0;
      int k = 0;
      for (int i = 0; i < buckets.length; i++) {
        if (buckets[i] > 0) {
          for (int j = 0; j < buckets[i]; j++) {
            if ((k & (~k + 1)) != 1) {
              sum += (minValue + i);
            }
            k++;
          }
        }
      }

      return sum;
    }

  }

}
