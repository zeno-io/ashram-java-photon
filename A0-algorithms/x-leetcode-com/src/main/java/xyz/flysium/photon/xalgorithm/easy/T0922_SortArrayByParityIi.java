package xyz.flysium.photon.xalgorithm.easy;

import java.util.Arrays;

/**
 * 922. 按奇偶排序数组 II
 * <p>
 * https://leetcode-cn.com/problems/sort-array-by-parity-ii/
 *
 * @author zeno
 */
public class T0922_SortArrayByParityIi {

//给定一个非负整数数组 A， A 中一半整数是奇数，一半整数是偶数。
//
// 对数组进行排序，以便当 A[i] 为奇数时，i 也是奇数；当 A[i] 为偶数时， i 也是偶数。
//
// 你可以返回任何满足上述条件的数组作为答案。
//
//
//
// 示例：
//
// 输入：[4,2,5,7]
//输出：[4,5,2,7]
//解释：[4,7,2,5]，[2,5,4,7]，[2,7,4,5] 也会被接受。
//
//
//
//
// 提示：
//
//
// 2 <= A.length <= 20000
// A.length % 2 == 0
// 0 <= A[i] <= 1000
//
//
//
// Related Topics 排序 数组
// 👍 182 👎 0


  public static void main(String[] args) {
    Solution solution = new T0922_SortArrayByParityIi().new Solution();
    System.out.println(Arrays.toString(solution.sortArrayByParityII(new int[]{3, 1, 4, 2})));
  }

  // 执行耗时:2 ms,击败了99.98% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] sortArrayByParityII(int[] A) {
      int[] ans = new int[A.length];
      int o = 1;
      int e = 0;
      for (int a : A) {
        if ((a & 1) == 1) {
          ans[o] = a;
          o += 2;
        } else {
          ans[e] = a;
          e += 2;
        }
      }
      return ans;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
