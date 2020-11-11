package xyz.flysium.photon.jianzhioffer.divide.easy;

/**
 * 剑指 Offer 17. 打印从1到最大的n位数
 * <p>
 * https://leetcode-cn.com/problems/da-yin-cong-1dao-zui-da-de-nwei-shu-lcof/
 *
 * @author zeno
 */
public class J0017_PrintNums {

//输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。
//
// 示例 1:
//
// 输入: n = 1
//输出: [1,2,3,4,5,6,7,8,9]
//
//
//
//
// 说明：
//
//
// 用返回一个整数列表来代替打印
// n 为正整数
//
// Related Topics 数学
// 👍 58 👎 0


  public static void main(String[] args) {
    Solution solution = new J0017_PrintNums().new Solution();
    System.out.println(solution.printNumbers(8));
  }

  // 执行用时：1 ms, 在所有 Java 提交中击败了99.98% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] printNumbers(int n) {
      int end = (int) (Math.pow(10, n) - 1);
      int[] res = new int[end];
      for (int i = 0; i < end; i++) {
        res[i] = i + 1;
      }
      return res;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
