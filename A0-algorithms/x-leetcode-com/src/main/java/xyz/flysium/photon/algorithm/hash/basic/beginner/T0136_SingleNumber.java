package xyz.flysium.photon.algorithm.hash.basic.beginner;

/**
 * 136. 只出现一次的数字
 * <p>
 * https://leetcode-cn.com/problems/single-number/
 *
 * @author zeno
 */
public interface T0136_SingleNumber {

  // 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
  // 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

  // 1ms 99.78%
  class Solution {

    public int singleNumber(int[] nums) {
      // ans ^ 0 = ans
      // ans ^ ans = 0
      int ans = 0;

      for (int num : nums) {
        ans ^= num;
      }

      return ans;
    }

  }

}
