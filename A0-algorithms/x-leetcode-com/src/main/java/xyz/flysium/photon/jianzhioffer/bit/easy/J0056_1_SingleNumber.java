package xyz.flysium.photon.jianzhioffer.bit.easy;

import java.util.Arrays;
import xyz.flysium.photon.ArraySupport;

/**
 * 剑指 Offer 56 - I. 数组中数字出现的次数
 * <p>
 * https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof/
 *
 * @author zeno
 */
public class J0056_1_SingleNumber {

//一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
//
//
//
// 示例 1：
//
// 输入：nums = [4,1,4,6]
//输出：[1,6] 或 [6,1]
//
//
// 示例 2：
//
// 输入：nums = [1,2,10,4,1,4,3,3]
//输出：[2,10] 或 [10,2]
//
//
//
// 限制：
//
//
// 2 <= nums.length <= 10000
//
//
//
// 👍 244 👎 0


  public static void main(String[] args) {
    Solution solution = new J0056_1_SingleNumber().new Solution();
    System.out.println(Arrays.toString(solution.singleNumbers(ArraySupport.newArray("[4,1,4,6]"))));
  }

  // 	执行耗时:2 ms,击败了96.00% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] singleNumbers(int[] nums) {
      int x = 0;

      for (int num : nums) {
        x = x ^ num;
      }

      // x = a ^ b != 0
      int div = x & (-x);
      int a = 0;
      for (int num : nums) {
        if ((num & div) == 0) {
          a = a ^ num;
        }
      }

      return new int[]{a, a ^ x};
    }

  }
//leetcode submit region end(Prohibit modification and deletion)

}
