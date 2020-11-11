package xyz.flysium.photon.algorithm.tree.trie.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 642. 设计搜索自动补全系统
 * <p>
 * https://leetcode-cn.com/problems/design-search-autocomplete-system/
 *
 * @author zeno
 */
public class T0642_DesignSearchAutocompleteSystem {

//为搜索引擎设计一个搜索自动补全系统。用户会输入一条语句（最少包含一个字母，以特殊字符 '#' 结尾）。除 '#' 以外用户输入的每个字符，返回历史中热度前三
//并以当前输入部分为前缀的句子。下面是详细规则：
//
//
// 一条句子的热度定义为历史上用户输入这个句子的总次数。
// 返回前三的句子需要按照热度从高到低排序（第一个是最热门的）。如果有多条热度相同的句子，请按照 ASCII 码的顺序输出（ASCII 码越小排名越前）。
// 如果满足条件的句子个数少于 3，将它们全部输出。
// 如果输入了特殊字符，意味着句子结束了，请返回一个空集合。
//
//
// 你的工作是实现以下功能：
//
// 构造函数：
//
// AutocompleteSystem(String[] sentences, int[] times): 这是构造函数，输入的是历史数据。 Sentenc
//es 是之前输入过的所有句子，Times 是每条句子输入的次数，你的系统需要记录这些历史信息。
//
// 现在，用户输入一条新的句子，下面的函数会提供用户输入的下一个字符：
//
// List<String> input(char c): 其中 c 是用户输入的下一个字符。字符只会是小写英文字母（'a' 到 'z' ），空格（' '）和
//特殊字符（'#'）。输出历史热度前三的具有相同前缀的句子。
//
//
//
// 样例 ：
//操作 ： AutocompleteSystem(["i love you", "island","ironman", "i love leetcode"],
// [5,3,2,2])
//系统记录下所有的句子和出现的次数：
//"i love you" : 5 次
//"island" : 3 次
//"ironman" : 2 次
//"i love leetcode" : 2 次
//现在，用户开始新的键入：
//
//
//输入 ： input('i')
//输出 ： ["i love you", "island","i love leetcode"]
//解释 ：
//有四个句子含有前缀 "i"。其中 "ironman" 和 "i love leetcode" 有相同的热度，由于 ' ' 的 ASCII 码是 32 而 '
//r' 的 ASCII 码是 114，所以 "i love leetcode" 在 "ironman" 前面。同时我们只输出前三的句子，所以 "ironman"
//被舍弃。
//
//输入 ： input(' ')
//输出 ： ["i love you","i love leetcode"]
//解释:
//只有两个句子含有前缀 "i "。
//
//输入 ： input('a')
//输出 ： []
//解释 ：
//没有句子有前缀 "i a"。
//
//输入 ： input('#')
//输出 ： []
//解释 ：
//
// 用户输入结束，"i a" 被存到系统中，后面的输入被认为是下一次搜索。
//
//
//
// 注释 ：
//
//
// 输入的句子以字母开头，以 '#' 结尾，两个字母之间最多只会出现一个空格。
// 即将搜索的句子总数不会超过 100。每条句子的长度（包括已经搜索的和即将搜索的）也不会超过 100。
// 即使只有一个字母，输出的时候请使用双引号而不是单引号。
// 请记住清零 AutocompleteSystem 类中的变量，因为静态变量、类变量会在多组测试数据中保存之前结果。详情请看这里。
//
//
//
// Related Topics 设计 字典树
// 👍 59 👎 0

  // 执行用时：134 ms, 在所有 Java 提交中击败了97.97% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class AutocompleteSystem {

    private static final int N_RANK = 3;
    private final StringBuilder input;
    private final TrieNode root;

    public AutocompleteSystem(String[] sentences, int[] times) {
      input = new StringBuilder();
      root = new TrieNode('\0');
      cur = root;
      for (int i = 0; i < sentences.length; i++) {
        insert(sentences[i], times[i]);
      }
    }

    private void insert(String sentence, int incrTime) {
      if (sentence == null || sentence.length() == 0) {
        return;
      }
      List<TrieNode> visisted = new LinkedList<>();
      TrieNode curr = root;
      curr.pass++;
      for (int i = 0; i < sentence.length(); i++) {
        char c = sentence.charAt(i);
        curr = putIfAbsent(curr, c);
        curr.pass++;
        visisted.add(curr);
      }
      curr.end++;
      curr.hotTime += incrTime;
      curr.sentence = sentence;

      for (TrieNode node : visisted) {
        node.update(curr);
      }
    }

    TrieNode cur;

    public List<String> input(char c) {
      if (c == '#') {
        // insert
        insert(input.toString(), 1);
        // clear
        input.delete(0, input.length());
        cur = root;
        return Collections.emptyList();
      }
      input.append(c);

      cur = get(cur, c);
      if (cur == null) {
        return Collections.emptyList();
      }
      if (cur.end > 0) {
        if (cur.pass == 1) {
          return Collections.singletonList(cur.sentence);
        }
      }

      LinkedList<String> ans = new LinkedList<>();
      for (int i = 0; i < cur.hot.size(); i++) {
        ans.add(cur.hot.get(i).sentence);
      }
      return ans;
    }

    private TrieNode putIfAbsent(TrieNode node, char c) {
      int idx = (c == ' ') ? 26 : c - 'a';
      TrieNode n = node.next[idx];
      if (n == null) {
        n = new TrieNode(c);
        node.next[idx] = n;
      }
      return n;
    }

    private TrieNode get(TrieNode node, char c) {
      int idx = (c == ' ') ? 26 : c - 'a';
      return node.next[idx];
    }

    class TrieNode implements Comparable<TrieNode> {

      final char c;
      int pass;
      int end;
      int hotTime;
      String sentence;
      final TrieNode[] next = new TrieNode[27];
      List<TrieNode> hot;

      TrieNode(char c) {
        this.c = c;
        hot = new ArrayList<>();
      }

      @Override
      public int compareTo(TrieNode o2) {
        // 热度从高到低排序（第一个是最热门的）   最小堆 -> 热度从低到高排序
        if (this.hotTime != o2.hotTime) {
          return this.hotTime - o2.hotTime;
        }
        // 如果有多条热度相同的句子，请按照 ASCII 码的顺序输出（ASCII 码越小排名越前）。
        // 最小堆 -> 热度从ASCII高到低排序
        return o2.sentence.compareTo(this.sentence);
      }

      public void update(TrieNode node) {
        if (!hot.contains(node)) {
          hot.add(node);
        }
        hot.sort((x, y) -> y.compareTo(x));
        while (hot.size() > N_RANK) {
          hot.remove(hot.size() - 1);
        }
      }

    }

  }

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */
//leetcode submit region end(Prohibit modification and deletion)


}
