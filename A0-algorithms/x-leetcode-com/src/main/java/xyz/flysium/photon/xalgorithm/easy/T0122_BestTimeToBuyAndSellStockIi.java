package xyz.flysium.photon.xalgorithm.easy;

/**
 * 122. 买卖股票的最佳时机 II
 * <p>
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/
 *
 * @author zeno
 */
public class T0122_BestTimeToBuyAndSellStockIi {

//给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
//
// 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
//
// 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
//
//
//
// 示例 1:
//
// 输入: [7,1,5,3,6,4]
//输出: 7
//解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//    随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
//
//
// 示例 2:
//
// 输入: [1,2,3,4,5]
//输出: 4
//解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//    注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
//    因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
//
//
// 示例 3:
//
// 输入: [7,6,4,3,1]
//输出: 0
//解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
//
//
//
// 提示：
//
//
// 1 <= prices.length <= 3 * 10 ^ 4
// 0 <= prices[i] <= 10 ^ 4
//
// Related Topics 贪心算法 数组
// 👍 905 👎 0


  public static void main(String[] args) {
    Solution solution = new T0122_BestTimeToBuyAndSellStockIi().new Solution();
    System.out.println(solution.maxProfit(new int[]{6, 1, 3, 2, 4, 7}));
  }

  // 执行耗时:1 ms,击败了99.55% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int maxProfit(int[] prices) {
      int i = 0;
      int length = prices.length;
      int ans = 0;
      while (i < length) {
        int minIdx = i;
        int j = i;
        while (j + 1 < length && prices[j + 1] < prices[minIdx]) {
          j++;
          minIdx = j;
        }
        if (j + 1 >= length) {
          break;
        }
        j = minIdx;
        int maxIdx = j;
        while (j + 1 < length && prices[j + 1] > prices[maxIdx]) {
          j++;
          maxIdx = j;
        }
        if (minIdx < maxIdx) {
          ans += prices[maxIdx] - prices[minIdx];
//          System.out.println(
//            minIdx + "(" + prices[minIdx] + ")"
//              + "," + maxIdx + "(" + prices[maxIdx] + ")");
        }

        i = maxIdx + 1;
      }
      return ans;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
