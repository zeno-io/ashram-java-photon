package xyz.flysium.photon.jianzhioffer.dynamic.medium;

import xyz.flysium.photon.ArraySupport;

/**
 * 剑指 Offer 63. 股票的最大利润
 * <p>
 * https://leetcode-cn.com/problems/gu-piao-de-zui-da-li-run-lcof/
 *
 * @author zeno
 */
public class J0063_MaxProfit {

//假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少？
//
//
//
// 示例 1:
//
// 输入: [7,1,5,3,6,4]
//输出: 5
//解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
//     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
//
//
// 示例 2:
//
// 输入: [7,6,4,3,1]
//输出: 0
//解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
//
//
//
// 限制：
//
// 0 <= 数组长度 <= 10^5
//
//
//
// 注意：本题与主站 121 题相同：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
// Related Topics 动态规划
// 👍 61 👎 0


  public static void main(String[] args) {
    Solution solution = new J0063_MaxProfit().new Solution();
    System.out.println(solution.maxProfit(ArraySupport.newArray("[7,1,5,3,6,4]")));
    System.out.println(solution.maxProfit(ArraySupport.newArray("[7,6,4,3,1]")));
    System.out.println(solution.maxProfit(ArraySupport.newArray("[7,0,4,3,1]")));
    System.out.println(solution.maxProfit(ArraySupport.newArray("[2,1,2,1,0,1,2]")));
    System.out.println(solution.maxProfit(ArraySupport.newArray("[3,3,5,0,0,3,1,4]")));
  }

  // 	执行用时：0 ms, 在所有 Java 提交中击败了100.00% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int maxProfit(int[] prices) {
      if (prices.length < 2) {
        return 0;
      }
      final int length = prices.length;
      // dp[i] 表示从0-i的最大价值
      // dp[i] = max (dp[i-1], prices[i] - min(prices[0]...prices[i]))
      int profit = 0;
      int min = Integer.MAX_VALUE;
      for (int i = 0; i < length; i++) {
        min = Math.min(min, prices[i]);
        profit = Math.max(profit, prices[i] - min);
      }

      return profit;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
