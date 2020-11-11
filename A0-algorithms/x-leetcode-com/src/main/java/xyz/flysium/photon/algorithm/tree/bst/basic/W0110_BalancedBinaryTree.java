package xyz.flysium.photon.algorithm.tree.bst.basic;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 110. 平衡二叉树
 * <p>
 * https://leetcode-cn.com/problems/balanced-binary-tree/
 *
 * @author zeno
 */
public interface W0110_BalancedBinaryTree {

  // 给定一个二叉树，判断它是否是高度平衡的二叉树。
  //
  // 本题中，一棵高度平衡二叉树定义为：
  //
  //    一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。

  // 1ms
  class Solution {

    public boolean isBalanced(TreeNode root) {
      if (root == null) {
        return true;
      }
      return Math.abs(depth(root.left) - depth(root.right)) <= 1
        && isBalanced(root.left)
        && isBalanced(root.right);
    }

    private int depth(TreeNode root) {
      if (root == null) {
        return 0;
      }
      return Math.max(depth(root.left), depth(root.right)) + 1;
    }

    // 2ms
//    public boolean isBalanced(TreeNode root) {
//      try {
//        depth(root);
//      } catch (IllegalStateException e) {
//        return false;
//      }
//      return true;
//    }
//
//    private int depth(TreeNode root) {
//      if (root == null) {
//        return 0;
//      }
//      int l = (root.left == null) ? 0 : depth(root.left);
//      int r = (root.right == null) ? 0 : depth(root.right);
//      if (Math.abs(l - r) > 1) {
//        throw new IllegalStateException();
//      }
//      return Math.max(l, r) + 1;
//    }

  }

}
