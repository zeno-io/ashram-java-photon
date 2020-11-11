package xyz.flysium.photon.jianzhioffer.sort.easy;

import java.util.HashSet;
import java.util.Set;

/**
 * 剑指 Offer 61. 扑克牌中的顺子
 * <p>
 * https://leetcode-cn.com/problems/bu-ke-pai-zhong-de-shun-zi-lcof/
 *
 * @author zeno
 */
public class J0061_IsStraight {

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
    Solution solution = new J0061_IsStraight().new Solution();
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

  // 执行用时：1 ms, 在所有 Java 提交中击败了91.60% 的用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean isStraight(int[] nums) {
      Set<Integer> s = new HashSet<>(5, 1);
      int min = Integer.MAX_VALUE;
      int max = Integer.MIN_VALUE;
      for (int i = 0; i < nums.length; i++) {
        int num = nums[i];
        if (num == 0) {
          continue;
        }
        // 若有重复，提前返回 false
        if (!s.add(num)) {
          return false;
        }
        min = Math.min(min, num);
        max = Math.max(max, num);
      }
      return max - min < 5; // 最大牌 - 最小牌 < 5 则可构成顺子
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
