package xyz.flysium.photon.algorithm.tree.binary.basic;

import java.util.Collections;
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
public interface T0094_BinaryTreeInorderTraversal_2 {

  // 中序遍历是先遍历左子树，然后访问根节点，然后遍历右子树。
  class Solution {

    public List<Integer> inorderTraversal(TreeNode root) {
      if (root == null) {
        return Collections.emptyList();
      }
      List<Integer> ans = new LinkedList<>();
      LinkedList<TreeNode> stack = new LinkedList<>();
      TreeNode node = root;
      while (!stack.isEmpty() || node != null) {
        while (node != null) {
          stack.push(node);
          node = node.left;
        }
        node = stack.pop();
        ans.add(node.val);
        node = node.right;
      }
      return ans;
    }

  }


}
