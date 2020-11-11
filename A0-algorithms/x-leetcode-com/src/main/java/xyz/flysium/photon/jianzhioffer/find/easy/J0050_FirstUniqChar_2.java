package xyz.flysium.photon.jianzhioffer.find.easy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 剑指 Offer 50. 第一个只出现一次的字符
 * <p>
 * https://leetcode-cn.com/problems/di-yi-ge-zhi-chu-xian-yi-ci-de-zi-fu-lcof/
 *
 * @author zeno
 */
public class J0050_FirstUniqChar_2 {

//在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
//
// 示例:
//
// s = "abaccdeff"
//返回 "b"
//
//s = ""
//返回 " "
//
//
//
//
// 限制：
//
// 0 <= s 的长度 <= 50000
// Related Topics 哈希表
// 👍 52 👎 0


  public static void main(String[] args) {
    Solution solution = new J0050_FirstUniqChar_2().new Solution();
    // l
    System.out.println(solution.firstUniqChar("leetcode"));
    // v
    System.out.println(solution.firstUniqChar("loveleetcode"));
  }

  // 执行用时：33 ms, 在所有 Java 提交中击败了32.54% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public char firstUniqChar(String s) {
      List<Character> res = new LinkedList<>();
      Map<Character, Integer> set = new HashMap<>(s.length(), 1);

      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        Integer cnt = set.put(c, set.getOrDefault(c, 0) + 1);
        if (cnt == null) {
          res.add(c);
        }
      }
      for (Character c : res) {
        if (set.getOrDefault(c, 0) == 1) {
          return c;
        }
      }
      return ' ';
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
