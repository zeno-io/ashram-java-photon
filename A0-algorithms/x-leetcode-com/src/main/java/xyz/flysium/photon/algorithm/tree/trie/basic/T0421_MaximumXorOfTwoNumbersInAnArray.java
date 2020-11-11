package xyz.flysium.photon.algorithm.tree.trie.basic;

/**
 * 421. 数组中两个数的最大异或值
 * <p>
 * https://leetcode-cn.com/problems/maximum-xor-of-two-numbers-in-an-array/
 *
 * @author zeno
 */
public class T0421_MaximumXorOfTwoNumbersInAnArray {

//给定一个非空数组，数组中元素为 a0, a1, a2, … , an-1，其中 0 ≤ ai < 2^31 。
//
// 找到 ai 和aj 最大的异或 (XOR) 运算结果，其中0 ≤ i, j < n 。
//
// 你能在O(n)的时间解决这个问题吗？
//
// 示例:
//
//
//输入: [3, 10, 5, 25, 2, 8]
//
//输出: 28
//
//解释: 最大的结果是 5 ^ 25 = 28.
//
// Related Topics 位运算 字典树
// 👍 174 👎 0


  public static void main(String[] args) {
    Solution solution = new T0421_MaximumXorOfTwoNumbersInAnArray().new Solution();
    System.out.println(Long.toBinaryString(7 | 0x100000000L).substring(1));
    System.out.println(Long.toBinaryString(Integer.MAX_VALUE | 0x100000000L).substring(1));
  }

  // 执行耗时:39 ms,击败了72.12% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int findMaximumXOR(int[] nums) {
      TrieNode trie = new TrieNode(0);
      int maxXor = 0;
      int currXor = 0;

      for (int num : nums) {
        // 32 bits
        String bits = Long.toBinaryString(num | 0x100000000L).substring(1);
        TrieNode node = trie;
        TrieNode xorNode = trie;
        currXor = 0;
        for (int i = 0; i < 32; i++) {
          int c = bits.charAt(i) - '0';
          TrieNode n = node.next[c];
          if (n == null) {
            n = new TrieNode(c);
            node.next[c] = n;
          }
          node = n;

          int tc = (c == 0) ? 1 : 0;
          TrieNode xn = xorNode.next[tc];
          if (xn == null) {
            currXor = currXor << 1;
            xorNode = xorNode.next[c];
          } else {
            currXor = (currXor << 1) | 1;
            xorNode = xorNode.next[tc];
          }
        }
        maxXor = Math.max(maxXor, currXor);
      }

      return maxXor;
    }

    class TrieNode {

      final int c;
      final TrieNode[] next = new TrieNode[2];

      TrieNode(int c) {
        this.c = c;
      }
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
