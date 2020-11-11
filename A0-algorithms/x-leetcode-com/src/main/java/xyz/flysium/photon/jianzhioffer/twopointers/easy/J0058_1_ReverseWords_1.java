package xyz.flysium.photon.jianzhioffer.twopointers.easy;

/**
 * 剑指 Offer 58 - I. 翻转单词顺序
 * <p>
 * https://leetcode-cn.com/problems/fan-zhuan-dan-ci-shun-xu-lcof/
 *
 * @author zeno
 */
public class J0058_1_ReverseWords_1 {

//输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，
//则输出"student. a am I"。
//
//
//
// 示例 1：
//
// 输入: "the sky is blue"
//输出:"blue is sky the"
//
//
// 示例 2：
//
// 输入: " hello world! "
//输出:"world! hello"
//解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
//
//
// 示例 3：
//
// 输入: "a good  example"
//输出:"example good a"
//解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
//
//
//
//
// 说明：
//
//
// 无空格字符构成一个单词。
// 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
// 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
//
//
// 注意：本题与主站 151 题相同：https://leetcode-cn.com/problems/reverse-words-in-a-string/
//
//
// 注意：此题对比原题有改动
// Related Topics 字符串
// 👍 42 👎 0


  public static void main(String[] args) {
    Solution solution = new J0058_1_ReverseWords_1().new Solution();
    System.out.println(solution.reverseWords("the sky is blue"));
    System.out.println(solution.reverseWords("   the sky is blue   "));
  }

  // 执行耗时:3 ms,击败了64.88% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public String reverseWords(String s) {
      StringBuilder buf = new StringBuilder();
      String pre = "";
      final int length = s.length();
      int r = length - 1;
      int l = r;
      while (l >= 0 && r >= 0) {
        r = l;
        while (r >= 0 && s.charAt(r) == ' ') {
          r--;
        }
        r++;
        l = r;
        while (l - 1 >= 0 && s.charAt(l - 1) != ' ') {
          l--;
        }
        if (l < 0) {
          l = 0;
        }
        if (r >= 0 && l < r) {
          buf.append(pre).append(s.substring(l, r));
          pre = " ";
        }
        l--;
      }

      return buf.toString();
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
