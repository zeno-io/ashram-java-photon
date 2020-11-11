package xyz.flysium.photon.algorithm.tree.binary.basic;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 112. 路径总和
 * <p>
 * https://leetcode-cn.com/problems/path-sum/
 *
 * @author zeno
 */
public interface U0112_PathSum {

  // 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
  // 说明: 叶子节点是指没有子节点的节点。

  class Solution {

    public boolean hasPathSum(TreeNode root, int sum) {
      if (root == null) {
        return false;
      }
      return hasPathSums(root, sum);
    }

    public boolean hasPathSums(TreeNode n, int sum) {
      if (n == null) {
        return false;
      }
      // leaf
      if (n != null && n.left == null && n.right == null && n.val == sum) {
        return true;
      }
      return hasPathSums(n.left, sum - n.val) || hasPathSums(n.right, sum - n.val);
    }

  }

}
