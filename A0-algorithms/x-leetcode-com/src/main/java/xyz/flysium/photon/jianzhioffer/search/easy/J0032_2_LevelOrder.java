package xyz.flysium.photon.jianzhioffer.search.easy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 32 - II. 从上到下打印二叉树 II
 * <p>
 * https://leetcode-cn.com/problems/cong-shang-dao-xia-da-yin-er-cha-shu-ii-lcof/
 *
 * @author zeno
 */
public class J0032_2_LevelOrder {

//从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。
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
// 返回其层次遍历结果：
//
// [
//  [3],
//  [9,20],
//  [15,7]
//]
//
//
//
//
// 提示：
//
//
// 节点总数 <= 1000
//
//
// 注意：本题与主站 102 题相同：https://leetcode-cn.com/problems/binary-tree-level-order-tra
//versal/
// Related Topics 树 广度优先搜索
// 👍 59 👎 0


  public static void main(String[] args) {
    Solution solution = new J0032_2_LevelOrder().new Solution();

  }

  // 执行耗时:1 ms,击败了93.35% 的Java用户
//leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    public List<List<Integer>> levelOrder(TreeNode root) {
      if (root == null) {
        return Collections.emptyList();
      }
      List<List<Integer>> l = new LinkedList<>();
      Queue<TreeNode> queue = new LinkedList<>();

      queue.offer(root);
      while (!queue.isEmpty()) {
        List<Integer> cl = new LinkedList<>();
        l.add(cl);
        int size = queue.size();
        for (int i = 0; i < size; i++) {
          TreeNode node = queue.poll();
          cl.add(node.val);
          if (node.left != null) {
            queue.offer(node.left);
          }
          if (node.right != null) {
            queue.offer(node.right);
          }
        }
      }
      return l;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
