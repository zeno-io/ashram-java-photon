package xyz.flysium.photon.jianzhioffer.search.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * 剑指 Offer 38. 字符串的排列
 * <p>
 * https://leetcode-cn.com/problems/zi-fu-chuan-de-pai-lie-lcof/
 *
 * @author zeno
 */
public class J0038_Permutation {

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
    Solution solution = new J0038_Permutation().new Solution();
    solution.permutation("abc");
  }

  // 	执行用时：4 ms, 在所有 Java 提交中击败了99.99% 的用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public String[] permutation(String s) {
      char[] cs = s.toCharArray();
      dfs(cs, 0);
      return ans.toArray(new String[0]);
    }

    List<String> ans = new ArrayList<>();

    private void dfs(char[] cs, int index) {
      if (index + 1 == cs.length) {
        ans.add(String.valueOf(cs));
        return;
      }
      boolean[] set = new boolean[128];
      for (int i = index; i < cs.length; i++) {
        if (set[cs[i]]) {
          continue;
        }
        set[cs[i]] = true;
        swap(cs, i, index);
        dfs(cs, index + 1);
        swap(cs, i, index);
      }
    }

    private void swap(char[] cs, int x, int y) {
      if (x == y) {
        return;
      }
      char c = cs[x];
      cs[x] = cs[y];
      cs[y] = c;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
