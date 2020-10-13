package xyz.flysium.photon.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的前序遍历、中序遍历、后序遍历 (递归方式)
 *
 * @author zeno
 */
public class T10_BinaryTreeTraversalWithinRecursion {

  // 前序遍历首先访问根节点，然后遍历左子树，最后遍历右子树。
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

  // 中序遍历是先遍历左子树，然后访问根节点，然后遍历右子树。
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

  // 后序遍历是先遍历左子树，然后遍历右子树，最后访问树的根节点。
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
