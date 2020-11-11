package xyz.flysium.photon.algorithm.string.easy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 1002. 查找常用字符
 * <p>
 * https://leetcode-cn.com/problems/find-common-characters/
 *
 * @author zeno
 */
public interface T1002_FindCommonCharacters {

  // 给定仅有小写字母组成的字符串数组 A，返回列表中的每个字符串中都显示的全部字符（包括重复字符）组成的列表。
  // 例如，如果一个字符在每个字符串中出现 3 次，但不是 4 次，则需要在最终答案中包含该字符 3 次。
  //
  //你可以按任意顺序返回答案。
  //

  // 2 ms 100.00%
  class Solution {

    private static final char[] CS = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public List<String> commonChars(String[] A) {
      if (A.length == 0 || A.length == 1) {
        return Collections.emptyList();
      }
      int[] hash = new int[26];
      for (int k = 0; k < A.length; k++) {
        String s = A[k];
        int[] tmp = (k == 0) ? hash : new int[26];
        // 只关注字母本身，使用getBytes 更高效
        for (byte b : s.getBytes()) {
          tmp[b - 97]++;
        }
        if (k > 0) {
          // 按 26 维度比较
          for (int index = 0; index < 26; index++) {
            if (tmp[index] < hash[index]) {
              hash[index] = tmp[index];
            }
          }
        }
      }
      List<String> ans = new LinkedList<>();
      for (int index = 0; index < 26; index++) {
        if (hash[index] > 0) {
          for (int i = 0; i < hash[index]; i++) {
            ans.add(String.valueOf(CS[index]));
          }
        }
      }
      return ans;
    }

  }

}
