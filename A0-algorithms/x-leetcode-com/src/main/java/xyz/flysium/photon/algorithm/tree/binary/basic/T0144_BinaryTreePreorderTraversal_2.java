package xyz.flysium.photon.algorithm.tree.binary.basic;

import java.util.Collections;
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
public interface T0144_BinaryTreePreorderTraversal_2 {

  // 前序遍历首先访问根节点，然后遍历左子树，最后遍历右子树。
  class Solution {

    public List<Integer> preorderTraversal(TreeNode root) {
      if (root == null) {
        return Collections.emptyList();
      }
      List<Integer> ans = new LinkedList<>();
      LinkedList<TreeNode> stack = new LinkedList<>();
      stack.push(root);
      while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        ans.add(node.val);
        if (node.right != null) {
          stack.push(node.right);
        }
        if (node.left != null) {
          stack.push(node.left);
        }
      }
      return ans;
    }

  }


}
