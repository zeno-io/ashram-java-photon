package xyz.flysium.photon.algorithm.tree.trie.basic;

/**
 * 677. 键值映射
 * <p>
 * https://leetcode-cn.com/problems/map-sum-pairs/
 *
 * @author zeno
 */
public class T0677_MapSumPairs {

//实现一个 MapSum 类，支持两个方法，insert 和 sum：
//
//
// MapSum() 初始化 MapSum 对象
// void insert(String key, int val) 插入 key-val 键值对，字符串表示键 key ，整数表示值 val 。如果键 ke
//y 已经存在，那么原来的键值对将被替代成新的键值对。
// int sum(string prefix) 返回所有以该前缀 prefix 开头的键 key 的值的总和。
//
//
//
//
// 示例：
//
//
//输入：
//["MapSum", "insert", "sum", "insert", "sum"]
//[[], ["apple", 3], ["ap"], ["app", 2], ["ap"]]
//输出：
//[null, null, 3, null, 5]
//
//解释：
//MapSum mapSum = new MapSum();
//mapSum.insert("apple", 3);
//mapSum.sum("ap");           // return 3 (apple = 3)
//mapSum.insert("app", 2);
//mapSum.sum("ap");           // return 5 (apple + app = 3 + 2 = 5)
//
//
//
//
// 提示：
//
//
// 1 <= key.length, prefix.length <= 50
// key 和 prefix 仅由小写英文字母组成
// 1 <= val <= 1000
// 最多调用 50 次 insert 和 sum
//
// Related Topics 字典树
// 👍 60 👎 0


  public static void main(String[] args) {
    MapSum solution = new T0677_MapSumPairs().new MapSum();
    solution.insert("apple", 3);
    // 3
    System.out.println(solution.sum("ap"));
    // 3
    System.out.println(solution.sum("apple"));
    solution.insert("app", 2);
    // 5
    System.out.println(solution.sum("ap"));
    solution.insert("apple", 2);
    // 4
    System.out.println(solution.sum("ap"));
    // 4
    System.out.println(solution.sum("a"));
  }

  // 执行耗时:14 ms,击败了99.64% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class MapSum {

    class TrieNode {

      final char c;
      int val;
      int sum;
      // key 和 prefix 仅由小写英文字母组成
      final TrieNode[] next = new TrieNode[26];

      TrieNode(char c) {
        this.c = c;
      }
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

    final TrieNode root;

    /**
     * Initialize your data structure here.
     */
    public MapSum() {
      root = new TrieNode('\0');
    }

    // 1 <= key.length, prefix.length <= 50
    // 1 <= val <= 1000
    public void insert(String key, int val) {
      if (key == null || key.length() == 0) {
        return;
      }
      TrieNode node = search(key);
      if (node != null && node.val == val) {
        return;
      }
      int incr = val - ((node != null) ? node.val : 0);

      TrieNode curr = root;
      curr.sum += incr;
      for (int i = 0; i < key.length(); i++) {
        char c = key.charAt(i);
        curr = putIfAbsent(curr, c);
        curr.sum += incr;
      }
      // 如果键 key 已经存在，那么原来的键值对将被替代成新的键值对。
      curr.val = val;
    }

    public int sum(String prefix) {
      TrieNode node = search(prefix);
      return (node == null) ? 0 : node.sum;
    }

    private TrieNode search(String key) {
      if (key == null || key.length() == 0) {
        return null;
      }
      TrieNode curr = root;
      for (int i = 0; i < key.length(); i++) {
        char c = key.charAt(i);
        curr = get(curr, c);
        if (curr == null) {
          return null;
        }
      }
      return curr;
    }

  }

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */
//leetcode submit region end(Prohibit modification and deletion)


}
