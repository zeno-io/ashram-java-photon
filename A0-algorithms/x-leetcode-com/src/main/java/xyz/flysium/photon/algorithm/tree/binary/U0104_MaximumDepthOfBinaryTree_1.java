package xyz.flysium.photon.algorithm.tree.binary;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 104. 二叉树的最大深度
 * <p>
 * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 *
 * @author zeno
 */
public interface U0104_MaximumDepthOfBinaryTree_1 {

  // “自顶向下” 的解决方案
  class Solution {

    public int maxDepth(TreeNode root) {
      ans = 0;
      maximumDepth(root, 1);
      return ans;
    }

    int ans = 0;

    private void maximumDepth(TreeNode node, int depth) {
      if (node == null) {
        return;
      }
      if (node.left == null && node.right == null) {
        ans = Math.max(ans, depth);
      }
      maximumDepth(node.left, depth + 1);
      maximumDepth(node.right, depth + 1);
    }

  }

}
