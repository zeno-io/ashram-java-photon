package xyz.flysium.photon.algorithm.tree.binary.basic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 145. 二叉树的后序遍历
 * <p>
 * https://leetcode-cn.com/problems/binary-tree-postorder-traversal/
 *
 * @author zeno
 */
public interface T0145_BinaryTreePostorderTraversal_2 {

  // 后序遍历是先遍历左子树，然后遍历右子树，最后访问树的根节点。
  class Solution {

    public List<Integer> postorderTraversal(TreeNode root) {
      if (root == null) {
        return Collections.emptyList();
      }
      LinkedList<Integer> ans = new LinkedList<>();
      LinkedList<TreeNode> stack = new LinkedList<>();
      stack.push(root);
      // 左，右，中 是 中，右，左的倒序，可以用前序遍历的变种
      while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        ans.addFirst(node.val);
        if (node.left != null) {
          stack.push(node.left);
        }
        if (node.right != null) {
          stack.push(node.right);
        }
      }
      return ans;
    }

  }


}
