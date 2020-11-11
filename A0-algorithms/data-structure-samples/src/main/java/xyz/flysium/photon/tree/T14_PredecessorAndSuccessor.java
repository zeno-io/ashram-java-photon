package xyz.flysium.photon.tree;

/**
 * 二叉树的中序遍历中，当前节点的前驱节点或后继节点
 *
 * @author zeno
 */
public class T14_PredecessorAndSuccessor {

  // One step left and then always right
  private TreeNode predecessor(TreeNode root) {
    TreeNode node = root.left;
    if (node == null) {
      return null;
    }
    while (node.right != null) {
      node = node.right;
    }
    return node;
  }

  // One step right and then always left
  private TreeNode successor(TreeNode root) {
    TreeNode node = root.right;
    if (node == null) {
      return null;
    }
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

}
