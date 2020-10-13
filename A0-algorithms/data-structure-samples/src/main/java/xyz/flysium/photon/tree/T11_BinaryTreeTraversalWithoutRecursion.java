package xyz.flysium.photon.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的前序遍历、中序遍历、后序遍历
 *
 * @author zeno
 */
public class T11_BinaryTreeTraversalWithoutRecursion {

  // 前序遍历首先访问根节点，然后遍历左子树，最后遍历右子树。
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

  // 中序遍历是先遍历左子树，然后访问根节点，然后遍历右子树。
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

  // 后序遍历是先遍历左子树，然后遍历右子树，最后访问树的根节点。
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

  public List<Integer> postorderTraversal2(TreeNode root) {
    if (root == null) {
      return Collections.emptyList();
    }
    LinkedList<Integer> ans = new LinkedList<>();
    LinkedList<TreeNode> stack = new LinkedList<>();
    stack.push(root);
    TreeNode node = root;
    TreeNode curr = null;
    while (!stack.isEmpty()) {
      curr = stack.peek();
      if (curr.left != null && node != curr.left && node != curr.right) {
        stack.push(curr.left);
      } else if (curr.right != null && node != curr.right) {
        stack.push(curr.right);
      } else {
        curr = stack.pop();
        ans.addLast(curr.val);
        node = curr;
      }
    }
    return ans;
  }

}
