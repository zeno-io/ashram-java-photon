package xyz.flysium.photon.algorithm.tree.bst;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 700. 二叉搜索树中的搜索
 * <p>
 * https://leetcode-cn.com/problems/search-in-a-binary-search-tree/
 *
 * @author zeno
 */
public interface T0700_SearchInABinarySearchTree {

  class Solution {

    public TreeNode searchBST(TreeNode root, int val) {
      return inorderTraversal(root, val);
    }

    private TreeNode inorderTraversal(TreeNode root, int val) {
      if (root == null) {
        return null;
      }
      if (root.val == val) {
        return root;
      }
      TreeNode node = this.inorderTraversal(root.left, val);
      if (node != null) {
        return node;
      }
      node = this.inorderTraversal(root.right, val);
      if (node != null) {
        return node;
      }
      return null;
    }

  }

}
