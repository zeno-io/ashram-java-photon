package xyz.flysium.photon.algorithm.tree.trie.hard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 425. 单词方块
 * <p>
 * https://leetcode-cn.com/problems/word-squares/
 *
 * @author zeno
 */
public class T0425_WordSquares {

//给定一个单词集合 （没有重复），找出其中所有的 单词方块 。
//
// 一个单词序列形成了一个有效的单词方块的意思是指从第 k 行和第 k 列 (0 ≤ k < max(行数, 列数)) 来看都是相同的字符串。
//
// 例如，单词序列 ["ball","area","lead","lady"] 形成了一个单词方块，因为每个单词从水平方向看和从竖直方向看都是相同的。
//
// b a l l
//a r e a
//l e a d
//l a d y
//
//
// 注意：
//
//
// 单词个数大于等于 1 且不超过 500。
// 所有的单词长度都相同。
// 单词长度大于等于 1 且不超过 5。
// 每个单词只包含小写英文字母 a-z。
//
//
//
//
// 示例 1：
//
// 输入：
//["area","lead","wall","lady","ball"]
//
//输出：
//[
//  [ "wall",
//    "area",
//    "lead",
//    "lady"
//  ],
//  [ "ball",
//    "area",
//    "lead",
//    "lady"
//  ]
//]
//
//解释：
//输出包含两个单词方块，输出的顺序不重要，只需要保证每个单词方块内的单词顺序正确即可。
//
//
//
//
// 示例 2：
//
// 输入：
//["abat","baba","atan","atal"]
//
//输出：
//[
//  [ "baba",
//    "abat",
//    "baba",
//    "atan"
//  ],
//  [ "baba",
//    "abat",
//    "baba",
//    "atal"
//  ]
//]
//
//解释：
//输出包含两个单词方块，输出的顺序不重要，只需要保证每个单词方块内的单词顺序正确即可。
//
//
//
// Related Topics 字典树 回溯算法
// 👍 38 👎 0

  // 执行用时：51 ms, 在所有 Java 提交中击败了68.42% 的用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    String[] words = null;

    public List<List<String>> wordSquares(String[] words) {
      this.words = words;

      // build Trie
      TrieNode trie = new TrieNode('\0');
      for (int wordIndex = 0; wordIndex < words.length; ++wordIndex) {
        String word = words[wordIndex];
        TrieNode node = trie;
        for (int i = 0; i < word.length(); i++) {
          char c = word.charAt(i);
          node = putIfAbsent(node, c);
          node.wordList.add(wordIndex);
        }
      }

      // 所有的单词长度都相同。
      final int sz = words[0].length();

      List<List<String>> ans = new LinkedList<>();
      for (String word : words) {
        LinkedList<String> wordSquares = new LinkedList<>();
        wordSquares.addLast(word);
        this.backtracking(trie, sz, 1, wordSquares, ans);
      }
      return ans;
    }

    private void backtracking(TrieNode trie, final int sz, int step, LinkedList<String> wordSquares,
      List<List<String>> ans) {
      if (step == sz) {
        ans.add((List<String>) wordSquares.clone());
        return;
      }

      StringBuilder prefix = new StringBuilder();
      for (String word : wordSquares) {
        prefix.append(word.charAt(step));
      }

      List<Integer> wordsWithPrefix = this.getWordsWithPrefix(trie, prefix.toString());
      for (int wordIndex : wordsWithPrefix) {
        wordSquares.addLast(this.words[wordIndex]);
        this.backtracking(trie, sz, step + 1, wordSquares, ans);
        wordSquares.removeLast();
      }
    }

    private List<Integer> getWordsWithPrefix(TrieNode trie, String prefix) {
      TrieNode node = trie;
      for (int i = 0; i < prefix.length(); i++) {
        char c = prefix.charAt(i);
        node = get(node, c);
        if (node == null) {
          // return an empty list.
          return Collections.emptyList();
        }
      }
      return node.wordList;
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
      final TrieNode[] next = new TrieNode[26];
      final List<Integer> wordList = new ArrayList<>();

      public TrieNode(char c) {
        this.c = c;
      }
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
