package xyz.flysium.photon.jianzhioffer.twopointers.easy;

import java.util.Arrays;

/**
 * 剑指 Offer 57. 和为s的两个数字
 * <p>
 * https://leetcode-cn.com/problems/he-wei-sde-liang-ge-shu-zi-lcof/
 *
 * @author zeno
 */
public class J0057_TwoSum {

//输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可。
//
//
//
// 示例 1：
//
// 输入：nums = [2,7,11,15], target = 9
//输出：[2,7] 或者 [7,2]
//
//
// 示例 2：
//
// 输入：nums = [10,26,30,31,47,60], target = 40
//输出：[10,30] 或者 [30,10]
//
//
//
//
// 限制：
//
//
// 1 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^6
//
// 👍 50 👎 0


  public static void main(String[] args) {
    Solution solution = new J0057_TwoSum().new Solution();

  }

  // 执行用时：2 ms, 在所有 Java 提交中击败了98.54% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] twoSum(int[] nums, int target) {
      //   1 <= nums.length <= 10^5
      //   1 <= nums[i] <= 10^6
      int i = Arrays.binarySearch(nums, target);
      if (i < 0) {
        i = -i - 1;
        if (i == nums.length) {
          i--;
        }
      }
      int l = 0;
      int r = i;
      while (l <= r) {
        if (nums[l] + nums[r] == target) {
          return new int[]{nums[l], nums[r]};
        } else if (nums[l] + nums[r] < target) {
          l++;
        } else {
          r--;
        }
      }
      return new int[]{};
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
