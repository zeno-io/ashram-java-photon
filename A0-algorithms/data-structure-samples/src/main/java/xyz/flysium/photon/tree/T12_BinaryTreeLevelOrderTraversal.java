package xyz.flysium.photon.tree;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的层序遍历
 *
 * @author zeno
 */
public class T12_BinaryTreeLevelOrderTraversal {

  // 层序遍历就是逐层遍历树结构。
  //
  // 广度优先搜索是一种广泛运用在树或图这类数据结构中，遍历或搜索的算法。
  // 该算法从一个根节点开始，首先访问节点本身。 然后遍历它的相邻节点，其次遍历它的二级邻节点、三级邻节点，以此类推。

  public List<Integer> simpleLevelOrder(TreeNode root) {
    if (root == null) {
      return Collections.emptyList();
    }
    List<Integer> ans = new LinkedList<>();
    TreeNode node = null;

    Deque<TreeNode> queue = new LinkedList<>();
    queue.offerLast(root);
    while (!queue.isEmpty()) {
      node = queue.pollFirst();
      ans.add(node.val);
      if (node.left != null) {
        queue.offerLast(node.left);
      }
      if (node.right != null) {
        queue.offerLast(node.right);
      }
    }
    return ans;
  }

  public List<List<Integer>> levelOrder(TreeNode root) {
    if (root == null) {
      return Collections.emptyList();
    }
    List<List<Integer>> ans = new LinkedList<>();
    TreeNode node = null;
    TreeNode currEnd = root;
    TreeNode nextEnd = null;
    int currLevel = 0;
    Deque<TreeNode> queue = new LinkedList<>();
    queue.offerLast(root);
    while (!queue.isEmpty()) {
      node = queue.pollFirst();
      while (currLevel >= ans.size()) {
        ans.add(new LinkedList<>());
      }
      ans.get(currLevel).add(node.val);
      if (node.left != null) {
        queue.offerLast(node.left);
        nextEnd = node.left;
      }
      if (node.right != null) {
        queue.offerLast(node.right);
        nextEnd = node.right;
      }
      if (node == currEnd) {
        currLevel++;
        currEnd = nextEnd;
      }
    }
    return ans;
  }

}
