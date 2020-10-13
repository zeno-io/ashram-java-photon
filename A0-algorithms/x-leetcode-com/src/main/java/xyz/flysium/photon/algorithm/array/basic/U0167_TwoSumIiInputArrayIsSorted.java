package xyz.flysium.photon.algorithm.array.basic;

/**
 * 167. 两数之和 II - 输入有序数组
 * <p>
 * https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/
 *
 * @author zeno
 */
public interface U0167_TwoSumIiInputArrayIsSorted {

  // 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
  // 函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。

  class Solution {

    public int[] twoSum(int[] numbers, int target) {
      int index1 = 0, index2 = numbers.length - 1;

      while (numbers[index1] + numbers[index2] != target) {
        if (numbers[index1] + numbers[index2] < target) {
          index1++;
        } else {
          index2--;
        }
      }

      return new int[]{index1 + 1, index2 + 1};
    }

  }

}
