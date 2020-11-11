package xyz.flysium.photon.jianzhioffer.sort.medium;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 剑指 Offer 45. 把数组排成最小的数
 * <p>
 * https://leetcode-cn.com/problems/ba-shu-zu-pai-cheng-zui-xiao-de-shu-lcof/
 *
 * @author zeno
 */
public class J0045_MinNumber_1 {

//输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
//
//
//
// 示例 1:
//
// 输入: [10,2]
//输出: "102"
//
// 示例 2:
//
// 输入: [3,30,34,5,9]
//输出: "3033459"
//
//
//
// 提示:
//
//
// 0 < nums.length <= 100
//
//
// 说明:
//
//
// 输出结果可能非常大，所以你需要返回一个字符串而不是整数
// 拼接起来的数字可能会有前导 0，最后结果不需要去掉前导 0
//
// Related Topics 排序
// 👍 108 👎 0


  public static void main(String[] args) {
    Solution solution = new J0045_MinNumber_1().new Solution();
    // 12112
    System.out.println(solution.minNumber(new int[]{12, 121}));
  }

  // 执行耗时:6 ms,击败了89.94% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public String minNumber(int[] nums) {
      Integer[] ns = new Integer[nums.length];
      for (int i = 0; i < nums.length; i++) {
        ns[i] = nums[i];
      }
      Arrays.sort(ns, new Comparator<Integer>() {

        @Override
        public int compare(Integer o1, Integer o2) {
          if (o1.equals(o2)) {
            return 0;
          }
          String s1 = String.valueOf(o1);
          String s2 = String.valueOf(o2);
          return compare0(s1, s2);
        }
      });
      StringBuilder buf = new StringBuilder();
      for (Integer n : ns) {
        buf.append(n);
      }
      return buf.toString();
    }

    private int compare0(String s1, String s2) {
      char c1 = s1.charAt(0);
      char c2 = s2.charAt(0);
      if (c1 < c2) {
        return -1;
      } else if (c2 < c1) {
        return 1;
      }
      String t1 = s1 + s2;
      String t2 = s2 + s1;
      for (int i = 0; i < t1.length(); i++) {
        c1 = t1.charAt(i);
        c2 = t2.charAt(i);
        if (c1 < c2) {
          return -1;
        } else if (c2 < c1) {
          return 1;
        }
      }
      return 0;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
