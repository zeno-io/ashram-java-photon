package xyz.flysium.photon.jianzhioffer.find.easy;

import java.util.Arrays;

/**
 * 剑指 Offer 53 - I. 在排序数组中查找数字 I
 * <p>
 * https://leetcode-cn.com/problems/zai-pai-xu-shu-zu-zhong-cha-zhao-shu-zi-lcof/
 *
 * @author zeno
 */
public class J0053_1_Search {

//统计一个数字在排序数组中出现的次数。
//
//
//
// 示例 1:
//
// 输入: nums = [5,7,7,8,8,10], target = 8
//输出: 2
//
// 示例 2:
//
// 输入: nums = [5,7,7,8,8,10], target = 6
//输出: 0
//
//
//
// 限制：
//
// 0 <= 数组长度 <= 50000
//
//
//
// 注意：本题与主站 34 题相同（仅返回值不同）：https://leetcode-cn.com/problems/find-first-and-last-
//position-of-element-in-sorted-array/
// Related Topics 数组 二分查找
// 👍 68 👎 0


  public static void main(String[] args) {
    Solution solution = new J0053_1_Search().new Solution();

  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int search(int[] nums, int target) {
      int i = Arrays.binarySearch(nums, target);
      if (i < 0) {
        return 0;
      }
      int t = i;
      while (i - 1 >= 0 && nums[i - 1] == target) {
        i--;
      }
      int l = i;
      i = t;
      while (i + 1 < nums.length && nums[i + 1] == target) {
        i++;
      }
      int r = i;
      return r - l + 1;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
