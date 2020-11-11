package xyz.flysium.photon.algorithm.tree.binary.basic;

import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 144. 二叉树的前序遍历
 * <p>
 * https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
 *
 * @author zeno
 */
public interface T0144_BinaryTreePreorderTraversal_1 {

  // 前序遍历首先访问根节点，然后遍历左子树，最后遍历右子树。
  class Solution {

    public List<Integer> preorderTraversal(TreeNode root) {
      List<Integer> ans = new LinkedList<>();
      preorderTraversal(root, ans);
      return ans;
    }

    public void preorderTraversal(TreeNode root, List<Integer> ans) {
      if (root == null) {
        return;
      }
      ans.add(root.val);
      preorderTraversal(root.left, ans);
      preorderTraversal(root.right, ans);
    }

  }


}
