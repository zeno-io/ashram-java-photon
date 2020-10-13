package xyz.flysium.photon.algorithm.tree.binary;

import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 94. 二叉树的中序遍历
 * <p>
 * https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 *
 * @author zeno
 */
public interface T0094_BinaryTreeInorderTraversal_1 {

  // 中序遍历是先遍历左子树，然后访问根节点，然后遍历右子树。
  class Solution {

    public List<Integer> inorderTraversal(TreeNode root) {
      List<Integer> ans = new LinkedList<>();
      inorderTraversal(root, ans);
      return ans;
    }

    public void inorderTraversal(TreeNode root, List<Integer> ans) {
      if (root == null) {
        return;
      }
      inorderTraversal(root.left, ans);
      ans.add(root.val);
      inorderTraversal(root.right, ans);
    }

  }


}
