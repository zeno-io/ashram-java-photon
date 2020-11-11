package xyz.flysium.photon.algorithm.tree.bst.basic;

import java.util.Deque;
import java.util.LinkedList;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 98. 验证二叉搜索树
 * <p>
 * https://leetcode-cn.com/problems/validate-binary-search-tree/
 *
 * @author zeno
 */
public interface T0096_ValidateBinarySearchTree_1 {

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
      // inorder traversal
      Deque<TreeNode> stack = new LinkedList<>();
      TreeNode node = root;
      TreeNode pred = null;

      while (!stack.isEmpty() || node != null) {
        while (node != null) {
          stack.push(node);
          node = node.left;
        }
        node = stack.pop();
        if (pred != null && pred.val >= node.val) {
          return false;
        }
        pred = node;
        node = node.right;
      }

      return true;
    }

  }

}
