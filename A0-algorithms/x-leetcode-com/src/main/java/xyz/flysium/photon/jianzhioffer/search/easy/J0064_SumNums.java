package xyz.flysium.photon.jianzhioffer.search.easy;

/**
 * 剑指 Offer 64. 求1+2+…+n
 * <p>
 * https://leetcode-cn.com/problems/qiu-12n-lcof/
 *
 * @author zeno
 */
public class J0064_SumNums {

//求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
//
//
//
// 示例 1：
//
// 输入: n = 3
//输出:6
//
//
// 示例 2：
//
// 输入: n = 9
//输出:45
//
//
//
//
// 限制：
//
//
// 1 <= n <= 10000
//
// 👍 218 👎 0


  public static void main(String[] args) {
    Solution solution = new J0064_SumNums().new Solution();

  }

  // 执行耗时:2 ms,击败了69.32% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    int res = 0;

    public int sumNums(int n) {
      boolean x = n > 1 && (sumNums(n - 1)) > 0;
      res += n;
      return res;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
