package xyz.flysium.photon.algorithm.tree.binary.basic;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 104. 二叉树的最大深度
 * <p>
 * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 *
 * @author zeno
 */
public interface U0104_MaximumDepthOfBinaryTree {

  // “自底向上” 的解决方案
  class Solution {

    public int maxDepth(TreeNode root) {
      return maximumDepth(root);
    }

    private int maximumDepth(TreeNode node) {
      if (node == null) {
        return 0;
      }
      int depthLeft = maximumDepth(node.left);
      int depthRight = maximumDepth(node.right);
      return Math.max(depthLeft, depthRight) + 1;
    }

  }

}
