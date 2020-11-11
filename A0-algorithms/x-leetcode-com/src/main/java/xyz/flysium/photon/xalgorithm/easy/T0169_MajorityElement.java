package xyz.flysium.photon.xalgorithm.easy;

import java.util.Arrays;

/**
 * 169. 多数元素
 * <p>
 * https://leetcode-cn.com/problems/majority-element/
 *
 * @author zeno
 */
public class T0169_MajorityElement {

//给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
//
// 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
//
//
//
// 示例 1:
//
// 输入: [3,2,3]
//输出: 3
//
// 示例 2:
//
// 输入: [2,2,1,1,1,2,2]
//输出: 2
//
// Related Topics 位运算 数组 分治算法
// 👍 778 👎 0


  public static void main(String[] args) {
    Solution solution = new T0169_MajorityElement().new Solution();

  }

  // 执行耗时:2 ms,击败了76.71% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int majorityElement(int[] nums) {
      if (nums == null) {
        throw new IllegalArgumentException();
      }
      if (nums.length <= 2) {
        return nums[0];
      }
      final int half = nums.length >> 1;
      Arrays.sort(nums);
      return nums[half];
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
