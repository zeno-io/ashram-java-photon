package xyz.flysium.photon.jianzhioffer.find.easy;

/**
 * 剑指 Offer 03. 数组中重复的数字
 * <p>
 * https://leetcode-cn.com/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof/
 *
 * @author zeno
 */
public class J0003_FindRepeatNumber_1 {

//找出数组中重复的数字。
//
//
//在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请
//找出数组中任意一个重复的数字。
//
// 示例 1：
//
// 输入：
//[2, 3, 1, 0, 2, 5, 3]
//输出：2 或 3
//
//
//
//
// 限制：
//
// 2 <= n <= 100000
// Related Topics 数组 哈希表
// 👍 197 👎 0


  public static void main(String[] args) {
    Solution solution = new J0003_FindRepeatNumber_1().new Solution();

  }

  // 执行耗时:2 ms,击败了68.36% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int findRepeatNumber(int[] nums) {
//      Set<Integer> set = new HashSet<>();
      boolean[] set = new boolean[100000];
      for (int num : nums) {
        if (set[num]) {
          return num;
        }
        set[num] = true;
      }
      return -1;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
