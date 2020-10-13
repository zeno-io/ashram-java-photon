package xyz.flysium.photon.algorithm.tree.binary;

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
public interface T0145_BinaryTreePostorderTraversal_1 {

  // 后序遍历是先遍历左子树，然后遍历右子树，最后访问树的根节点。
  class Solution {

    public List<Integer> postorderTraversal(TreeNode root) {
      List<Integer> ans = new LinkedList<>();
      postorderTraversal(root, ans);
      return ans;
    }

    public void postorderTraversal(TreeNode root, List<Integer> ans) {
      if (root == null) {
        return;
      }
      postorderTraversal(root.left, ans);
      postorderTraversal(root.right, ans);
      ans.add(root.val);
    }

  }


}
