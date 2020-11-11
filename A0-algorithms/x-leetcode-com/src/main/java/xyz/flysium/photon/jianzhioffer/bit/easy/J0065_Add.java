package xyz.flysium.photon.jianzhioffer.bit.easy;

/**
 * 剑指 Offer 65. 不用加减乘除做加法
 * <p>
 * https://leetcode-cn.com/problems/bu-yong-jia-jian-cheng-chu-zuo-jia-fa-lcof/
 *
 * @author zeno
 */
public class J0065_Add {

//写一个函数，求两个整数之和，要求在函数体内不得使用 “+”、“-”、“*”、“/” 四则运算符号。
//
//
//
// 示例:
//
// 输入: a = 1, b = 1
//输出: 2
//
//
//
// 提示：
//
//
// a, b 均可能是负数或 0
// 结果不会溢出 32 位整数
//
// 👍 78 👎 0


  public static void main(String[] args) {
    Solution solution = new J0065_Add().new Solution();
    System.out.println(solution.add(20, 17));
  }

  // 执行用时：0 ms, 在所有 Java 提交中击败了100.00% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int add(int a, int b) {
      while (b != 0) { // 当进位为 0 时跳出
        int c = (a & b) << 1;  // c = 进位
        a = a ^ b; // a = 非进位和
        b = c; // b = 进位
      }
      return a;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
