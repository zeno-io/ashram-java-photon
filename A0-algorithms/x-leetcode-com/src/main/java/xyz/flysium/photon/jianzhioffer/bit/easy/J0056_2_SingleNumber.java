package xyz.flysium.photon.jianzhioffer.bit.easy;

/**
 * 剑指 Offer 56 - II. 数组中数字出现的次数 II
 * <p>
 * https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-ii-lcof/
 *
 * @author zeno
 */
public class J0056_2_SingleNumber {

//在一个数组 nums 中除一个数字只出现一次之外，其他数字都出现了三次。请找出那个只出现一次的数字。
//
//
//
// 示例 1：
//
// 输入：nums = [3,4,3,3]
//输出：4
//
//
// 示例 2：
//
// 输入：nums = [9,1,7,9,7,9,7]
//输出：1
//
//
//
// 限制：
//
//
// 1 <= nums.length <= 10000
// 1 <= nums[i] < 2^31
//
//
//
// 👍 92 👎 0


  public static void main(String[] args) {
    Solution solution = new J0056_2_SingleNumber().new Solution();

  }

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int singleNumber(int[] nums) {
      int[] bits = new int[32];

      for (int num : nums) {
        for (int i = 0; i < 32; i++) {
          bits[i] += (num & 1);
          num = num >>> 1;
        }
      }

      int ans = 0;
      final int k = 3;
      for (int i = 31; i >= 0; i--) {
        ans <<= 1;
        ans = ans | (bits[i] % k);
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
