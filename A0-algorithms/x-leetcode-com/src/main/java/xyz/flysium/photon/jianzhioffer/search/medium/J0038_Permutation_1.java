package xyz.flysium.photon.jianzhioffer.search.medium;

import java.util.HashSet;
import java.util.Set;

/**
 * 剑指 Offer 38. 字符串的排列
 * <p>
 * https://leetcode-cn.com/problems/zi-fu-chuan-de-pai-lie-lcof/
 *
 * @author zeno
 */
public class J0038_Permutation_1 {

//输入一个字符串，打印出该字符串中字符的所有排列。
//
//
//
// 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
//
//
//
// 示例:
//
// 输入：s = "abc"
//输出：["abc","acb","bac","bca","cab","cba"]
//
//
//
//
// 限制：
//
// 1 <= s 的长度 <= 8
// Related Topics 回溯算法
// 👍 118 👎 0


  public static void main(String[] args) {
    Solution solution = new J0038_Permutation_1().new Solution();
    solution.permutation("abc");
  }

  // 	执行耗时:83 ms,击败了7.45% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public String[] permutation(String s) {
      for (int i = 0; i < s.length(); i++) {
        dfs(s, i, 1, s.length());
      }
      return ans.toArray(new String[0]);
    }

    Set<String> ans = new HashSet<>();
    StringBuilder buf = new StringBuilder();
    Set<Integer> set = new HashSet<>();

    private void dfs(String s, int index, int s0, int k) {
      if (s0 > k) {
        return;
      }
      set.add(index);
      buf.append(s.charAt(index));
      if (s0 == k) {
        ans.add(buf.toString());
      }
      int next = index + 1;
      for (int i = 0; i < k - s0; i++) {
        next = next % s.length();
        while (set.contains(next)) {
          next++;
          if (next >= s.length()) {
            next = 0;
          }
        }
        dfs(s, next++, s0 + 1, k);
      }
      buf.deleteCharAt(buf.length() - 1);
      set.remove(index);
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
