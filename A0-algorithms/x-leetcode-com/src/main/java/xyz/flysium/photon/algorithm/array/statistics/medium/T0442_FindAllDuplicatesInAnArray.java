package xyz.flysium.photon.algorithm.array.statistics.medium;

import java.util.LinkedList;
import java.util.List;

/**
 * 442. 数组中重复的数据
 * <p>
 * https://leetcode-cn.com/problems/find-all-duplicates-in-an-array/
 *
 * @author zeno
 */
public class T0442_FindAllDuplicatesInAnArray {

  // 给定一个整数数组 a，其中1 ≤ a[i] ≤ n （n为数组长度）, 其中有些元素出现两次而其他元素出现一次。
  // 找到所有出现两次的元素。
  // 你可以不用到任何额外空间并在O(n)时间复杂度内解决这个问题吗？
  static class Solution {

    public List<Integer> findDuplicates(int[] nums) {
      LinkedList<Integer> ans = new LinkedList<>();
      // nums:   4, 3, 2, 7, 8, 2, 3, 1
      // offset: 0, 1, 2, 3, 4, 5, 6, 7
      int num = 0;
      int numIndex = 0;
      for (int i = 0; i < nums.length; i++) {
        num = Math.abs(nums[i]);
        numIndex = num - 1;
        if (nums[numIndex] > 0) {
          nums[numIndex] = -nums[numIndex];
        } else {
          ans.addLast(num);
        }
      }
      return ans;
    }

  }

}
