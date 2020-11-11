package xyz.flysium.photon.algorithm.tree.binary.basic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 102. 二叉树的层序遍历
 * <p>
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 *
 * @author zeno
 */
public interface T0102_BinaryTreeLevelOrderTraversal {

  // 层序遍历就是逐层遍历树结构。
  //
  // 广度优先搜索是一种广泛运用在树或图这类数据结构中，遍历或搜索的算法。
  // 该算法从一个根节点开始，首先访问节点本身。 然后遍历它的相邻节点，其次遍历它的二级邻节点、三级邻节点，以此类推。
  class Solution {

    public List<List<Integer>> levelOrder(TreeNode root) {
      if (root == null) {
        return Collections.emptyList();
      }
      List<List<Integer>> ans = new LinkedList<>();
      order(root, ans, 0);
      return ans;
    }

    private void order(TreeNode root, List<List<Integer>> list, int depth) {
      if (root == null) {
        return;
      }
      if (depth >= list.size()) {
        list.add(new LinkedList<>());
      }
      list.get(depth).add(root.val);
      if (root.left != null) {
        order(root.left, list, depth + 1);
      }
      if (root.right != null) {
        order(root.right, list, depth + 1);
      }
    }

  }
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

}
