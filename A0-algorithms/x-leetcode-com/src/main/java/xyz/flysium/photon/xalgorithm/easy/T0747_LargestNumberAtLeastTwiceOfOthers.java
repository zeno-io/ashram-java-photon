package xyz.flysium.photon.xalgorithm.easy;

/**
 * 747. 至少是其他数字两倍的最大数
 * <p>
 * https://leetcode-cn.com/problems/largest-number-at-least-twice-of-others/
 *
 * @author zeno
 */
public class T0747_LargestNumberAtLeastTwiceOfOthers {

//在一个给定的数组nums中，总是存在一个最大元素 。
//
// 查找数组中的最大元素是否至少是数组中每个其他数字的两倍。
//
// 如果是，则返回最大元素的索引，否则返回-1。
//
// 示例 1:
//
// 输入: nums = [3, 6, 1, 0]
//输出: 1
//解释: 6是最大的整数, 对于数组中的其他整数,
//6大于数组中其他元素的两倍。6的索引是1, 所以我们返回1.
//
//
//
//
// 示例 2:
//
// 输入: nums = [1, 2, 3, 4]
//输出: -1
//解释: 4没有超过3的两倍大, 所以我们返回 -1.
//
//
//
//
// 提示:
//
//
// nums 的长度范围在[1, 50].
// 每个 nums[i] 的整数范围在 [0, 100].
//
// Related Topics 数组
// 👍 72 👎 0


  public static void main(String[] args) {
    Solution solution = new T0747_LargestNumberAtLeastTwiceOfOthers().new Solution();

  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // too easy ~
    public int dominantIndex(int[] nums) {
      int maxIdx = 0;
      int max = nums[0];
      for (int i = 1; i < nums.length; i++) {
        if (nums[i] > max) {
          max = nums[i];
          maxIdx = i;
        }
      }
      for (int i = 0; i < nums.length; i++) {
        if (i != maxIdx && (nums[i] << 1) > max) {
          return -1;
        }
      }
      return maxIdx;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
