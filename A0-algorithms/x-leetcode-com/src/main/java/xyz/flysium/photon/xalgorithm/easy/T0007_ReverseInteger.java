package xyz.flysium.photon.xalgorithm.easy;

/**
 * 7. 整数反转
 * <p>
 * https://leetcode-cn.com/problems/reverse-integer/
 *
 * @author zeno
 */
public class T0007_ReverseInteger {

//给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
//
// 示例 1:
//
// 输入: 123
//输出: 321
//
//
// 示例 2:
//
// 输入: -123
//输出: -321
//
//
// 示例 3:
//
// 输入: 120
//输出: 21
//
//
// 注意:
//
// 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231, 231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
// Related Topics 数学
// 👍 2291 👎 0


  public static void main(String[] args) {
    Solution solution = new T0007_ReverseInteger().new Solution();
    System.out.println(Integer.MAX_VALUE);
  }

  // 执行耗时:1 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int reverse(int x) {
      boolean negative = x < 0;
      long a = Math.abs((long) x);
      long ans = 0;
      while (a != 0) {
        long b = a % 10;
        ans = ans * 10 + b;
        if (negative && ans - 1 > Integer.MAX_VALUE) {
          return 0;
        }
        if (!negative && ans > Integer.MAX_VALUE) {
          return 0;
        }
        a = a / 10;
      }

      return negative ? -(int) ans : (int) ans;
    }

//    public int reverse(int x) {
//      String s = Integer.toString(x);
//      // 2147483647 -> 7463847412 (overflow) -> 1563847412
//      String rx = reverse(s, x < 0 ? 1 : 0, s.length() - 1);
//      long ans = Long.parseLong(rx);
//      if (ans > Integer.MAX_VALUE || ans < Integer.MIN_VALUE) {
//        return 0;
//      }
//      return (int) ans;
//    }
//
//    private String reverse(String s, int l, int r) {
//      int halfLen = ((r - l + 1) >> 1);
//      char[] cs = s.toCharArray();
//      for (int i = 0; i < halfLen; i++) {
//        // swap
//        char t = cs[l + i];
//        cs[l + i] = cs[r - i];
//        cs[r - i] = t;
//      }
//      return String.valueOf(cs);
//    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
