package xyz.flysium.photon.algorithm.tree.bst;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 98. 验证二叉搜索树
 * <p>
 * https://leetcode-cn.com/problems/validate-binary-search-tree/
 *
 * @author zeno
 */
public interface T0096_ValidateBinarySearchTree {

  // 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
  //
  // 假设一个二叉搜索树具有如下特征：
  //
  //    节点的左子树只包含小于当前节点的数。
  //    节点的右子树只包含大于当前节点的数。
  //    所有左子树和右子树自身必须也是二叉搜索树。

  class Solution {

    public boolean isValidBST(TreeNode root) {
      if (root == null) {
        return true;
      }
      return isValidBST(root, null, null);
    }

    private boolean isValidBST(TreeNode root, Integer lower, Integer upper) {
      if (root == null) {
        return true;
      }
      int value = root.val;
      if (lower != null && lower >= value) {
        return false;
      }
      if (upper != null && upper <= value) {
        return false;
      }
      if (!isValidBST(root.left, lower, value)) {
        return false;
      }
      if (!isValidBST(root.right, value, upper)) {
        return false;
      }
      return true;
    }

  }

}
