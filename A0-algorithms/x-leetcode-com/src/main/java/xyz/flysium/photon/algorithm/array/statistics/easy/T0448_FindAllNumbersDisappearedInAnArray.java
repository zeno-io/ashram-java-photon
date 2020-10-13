package xyz.flysium.photon.algorithm.array.statistics.easy;

import java.util.LinkedList;
import java.util.List;

/**
 * 448. 找到所有数组中消失的数字
 * <p>
 * https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array/
 *
 * @author zeno
 */
public class T0448_FindAllNumbersDisappearedInAnArray {

  // 给定一个范围在  1 ≤ a[i] ≤ n ( n = 数组大小 ) 的 整型数组，数组中的元素一些出现了两次，另一些只出现一次。
  // 您能在不使用额外空间且时间复杂度为O(n)的情况下完成这个任务吗? 你可以假定返回的数组不算在额外空间内。
  static class Solution {

    public List<Integer> findDisappearedNumbers(int[] nums) {
      LinkedList<Integer> ans = new LinkedList<>();
      // nums:   4, 3, 2, 7, 8, 2, 3, 1
      // offset: 0, 1, 2, 3, 4, 5, 6, 7
      // offset(0)=4  ->  offset(4-1) = -7
      // ...
      // offset(4-1) = -7 -> 7 -> offset(7-1) = -3
      // Math.abs(nums[i]) -> nums[Math.abs(nums[i])-1] to negative
      // nums:   -4, -3, -2, -7, 8, 2, -3, -1
      for (int i = 0; i < nums.length; i++) {
        int numIndex = Math.abs(nums[i]) - 1;
        if (nums[numIndex] > 0) {
          nums[numIndex] = -nums[numIndex];
        }
      }
      for (int i = 1; i <= nums.length; i++) {
        if (nums[i - 1] > 0) {
          ans.addLast(i);
        }
      }
      return ans;
    }

  }

}
