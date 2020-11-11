package xyz.flysium.photon.jianzhioffer.bit.easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import xyz.flysium.photon.ArraySupport;

/**
 * 剑指 Offer 56 - I. 数组中数字出现的次数
 * <p>
 * https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof/
 *
 * @author zeno
 */
public class J0056_1_SingleNumber_1 {

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
    Solution solution = new J0056_1_SingleNumber_1().new Solution();
    System.out.println(Arrays.toString(solution.singleNumbers(ArraySupport.newArray("[4,1,4,6]"))));
  }

  // 执行耗时:11 ms,击败了16.86% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] singleNumbers(int[] nums) {
      Set<Integer> set = new HashSet<>();
      for (int num : nums) {
        if (set.contains(num)) {
          set.remove(num);
        } else {
          set.add(num);
        }
      }
      int[] ans = new int[2];
      int i = 0;
      for (Integer e : set) {
        ans[i++] = e;
      }
      return ans;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
