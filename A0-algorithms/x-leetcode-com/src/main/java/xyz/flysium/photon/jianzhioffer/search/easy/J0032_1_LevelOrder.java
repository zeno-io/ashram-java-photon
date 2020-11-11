package xyz.flysium.photon.jianzhioffer.search.easy;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 32 - I. 从上到下打印二叉树
 * <p>
 * https://leetcode-cn.com/problems/cong-shang-dao-xia-da-yin-er-cha-shu-lcof/
 *
 * @author zeno
 */
public class J0032_1_LevelOrder {

//从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。
//
//
//
// 例如:
//给定二叉树: [3,9,20,null,null,15,7],
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
//
//
// 返回：
//
// [3,9,20,15,7]
//
//
//
//
// 提示：
//
//
// 节点总数 <= 1000
//
// Related Topics 树 广度优先搜索
// 👍 45 👎 0


  public static void main(String[] args) {
    Solution solution = new J0032_1_LevelOrder().new Solution();

  }

  // 执行耗时:3 ms,击败了16.70% 的Java用户
//leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    public int[] levelOrder(TreeNode root) {
      if (root == null) {
        return new int[0];
      }
      List<Integer> l = new LinkedList<>();
      Queue<TreeNode> queue = new LinkedList<>();

      queue.offer(root);
      while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
          TreeNode node = queue.poll();
          l.add(node.val);
          if (node.left != null) {
            queue.offer(node.left);
          }
          if (node.right != null) {
            queue.offer(node.right);
          }
        }
      }
      int[] ans = new int[l.size()];
      for (int i = 0; i < l.size(); i++) {
        ans[i] = l.get(i);
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
