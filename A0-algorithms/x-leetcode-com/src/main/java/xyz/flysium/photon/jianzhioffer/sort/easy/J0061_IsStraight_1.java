package xyz.flysium.photon.jianzhioffer.sort.easy;

import java.util.Arrays;

/**
 * 剑指 Offer 61. 扑克牌中的顺子
 * <p>
 * https://leetcode-cn.com/problems/bu-ke-pai-zhong-de-shun-zi-lcof/
 *
 * @author zeno
 */
public class J0061_IsStraight_1 {

//从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任
//意数字。A 不能视为 14。
//
//
//
// 示例 1:
//
// 输入: [1,2,3,4,5]
//输出: True
//
//
//
// 示例 2:
//
// 输入: [0,0,1,2,5]
//输出: True
//
//
//
// 限制：
//
// 数组长度为 5
//
// 数组的数取值为 [0, 13] .
// 👍 72 👎 0


  public static void main(String[] args) {
    Solution solution = new J0061_IsStraight_1().new Solution();
    // true
    System.out.println(solution.isStraight(new int[]{1, 2, 3, 4, 5}));
    // true
    System.out.println(solution.isStraight(new int[]{0, 0, 1, 2, 5}));
    // false
    System.out.println(solution.isStraight(new int[]{0, 0, 1, 2, 6}));
    // true
    System.out.println(solution.isStraight(new int[]{0, 5, 6, 0, 7}));
    // false
    System.out.println(solution.isStraight(new int[]{0, 1, 13, 3, 5}));
    // true
    System.out.println(solution.isStraight(new int[]{11, 0, 9, 0, 0}));
    // false
    System.out.println(solution.isStraight(new int[]{0, 12, 11, 11, 0}));
  }

  // 执行耗时:1 ms,击败了91.60% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean isStraight(int[] nums) {
      Arrays.sort(nums);
      int n = 0;
      while (nums[n] == 0) {
        n++;
      }
      int min = nums[n];
      int i = n + 1;
      while (i < nums.length && nums[i] == nums[i - 1] + 1) {
        i++;
      }
      if (i == nums.length) {
        return true;
      }
      if (nums[i] - nums[i - 1] > 0 && nums[i] - nums[i - 1] <= n + 1) {
        i++;
        while (i < nums.length && nums[i] == nums[i - 1] + 1) {
          i++;
        }
        return i == nums.length;
      }
      return false;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
