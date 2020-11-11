package xyz.flysium.photon.algorithm.tree.trie.basic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 336. 回文对
 * <p>
 * https://leetcode-cn.com/problems/palindrome-pairs/
 *
 * @author zeno
 */
public class T0336_PalindromePairs {

//给定一组 互不相同 的单词， 找出所有不同 的索引对(i, j)，使得列表中的两个单词， words[i] + words[j] ，可拼接成回文串。
//
//
//
// 示例 1：
//
// 输入：["abcd","dcba","lls","s","sssll"]
//输出：[[0,1],[1,0],[3,2],[2,4]]
//解释：可拼接成的回文串为 ["dcbaabcd","abcddcba","slls","llssssll"]
//
//
// 示例 2：
//
// 输入：["bat","tab","cat"]
//输出：[[0,1],[1,0]]
//解释：可拼接成的回文串为 ["battab","tabbat"]
// Related Topics 字典树 哈希表 字符串
// 👍 190 👎 0


  public static void main(String[] args) {
    Solution solution = new T0336_PalindromePairs().new Solution();

  }

  // 执行耗时:44 ms,击败了96.94% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public List<List<Integer>> palindromePairs(String[] words) {
      TrieNode trie = new TrieNode('\0');
      final int len = words.length;
      for (int i = 0; i < len; i++) {
        insert(trie, words[i], i);
      }

      List<List<Integer>> ans = new LinkedList<>();
      for (int i = 0; i < len; i++) {
        int m = words[i].length();
        for (int j = 0; j <= m; j++) {
          if (isPalindrome(words[i], j, m - 1)) {
            TrieNode left = findWord(trie, words[i], 0, j - 1);
            if (left != null && left.wordIdx >= 0 && left.wordIdx != i) {
              ans.add(Arrays.asList(i, left.wordIdx));
            }
          }
          if (j != 0 && isPalindrome(words[i], 0, j - 1)) {
            TrieNode right = findWord(trie, words[i], j, m - 1);
            if (right != null && right.wordIdx >= 0 && right.wordIdx != i) {
              ans.add(Arrays.asList(right.wordIdx, i));
            }
          }
        }
      }
      return ans;
    }

    private boolean isPalindrome(String s, int left, int right) {
      int len = right - left + 1;
      for (int i = 0; i < len / 2; i++) {
        if (s.charAt(left + i) != s.charAt(right - i)) {
          return false;
        }
      }
      return true;
    }

    private void insert(TrieNode trie, String s, int id) {
      TrieNode node = trie;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        node = putIfAbsent(node, c);
      }
      node.wordIdx = id;
    }

    private TrieNode findWord(TrieNode trie, String s, int left, int right) {
      TrieNode node = trie;
      for (int i = right; i >= left; i--) {
        char c = s.charAt(i);
        node = get(node, c);
        if (node == null) {
          return null;
        }
      }
      return node;
    }

    private TrieNode putIfAbsent(TrieNode node, char c) {
      TrieNode n = node.next[c - 'a'];
      if (n == null) {
        n = new TrieNode(c);
        node.next[c - 'a'] = n;
      }
      return n;
    }

    private TrieNode get(TrieNode node, char c) {
      return node.next[c - 'a'];
    }

    class TrieNode {

      final char c;
      int wordIdx = -1;
      final TrieNode[] next = new TrieNode[26];

      public TrieNode(char c) {
        this.c = c;
      }
    }

  }
  //leetcode submit region end(Prohibit modification and deletion)

}
