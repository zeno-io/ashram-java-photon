package xyz.flysium.photon.algorithm.hash.basic.beginner;

/**
 * 136. 只出现一次的数字
 * <p>
 * https://leetcode-cn.com/problems/single-number/
 *
 * @author zeno
 */
public interface T0136_SingleNumber_2 {

  // 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
  // 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

  // 2 ms 32.66%
  class Solution {

    public int singleNumber(int[] nums) {
      int min = Integer.MAX_VALUE;
      int max = Integer.MIN_VALUE;
      for (int num : nums) {
        if (num > max) {
          max = num;
        }
        if (num < min) {
          min = num;
        }
      }
      boolean[] hash = new boolean[max - min + 1];

      for (int num : nums) {
        if (hash[num - min]) {
          hash[num - min] = false;
        } else {
          hash[num - min] = true;
        }
      }

      for (int i = 0; i < hash.length; i++) {
        if (hash[i]) {
          return min + i;
        }
      }

      return -1;
    }

  }

}
