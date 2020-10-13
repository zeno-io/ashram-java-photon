package xyz.flysium.photon.tree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * TODO description
 *
 * @author zeno
 */
public class TreeNode implements ITreeNode {

  public int val;
  public TreeNode left;
  public TreeNode right;

  public TreeNode() {
  }

  public TreeNode(int val) {
    this.val = val;
  }

  public TreeNode(int val, TreeNode left, TreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }

  @Override
  public int getVal() {
    return val;
  }

  @Override
  public void setVal(int val) {
    this.val = val;
  }

  @Override
  public TreeNode getLeft() {
    return left;
  }

  @Override
  public void setLeft(ITreeNode left) {
    this.left = (TreeNode) left;
  }

  @Override
  public TreeNode getRight() {
    return right;
  }

  @Override
  public void setRight(ITreeNode right) {
    this.right = (TreeNode) right;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder("[");
    String pre = "";
    TreeNode node = null;
    TreeNode currEnd = this;
    TreeNode nextEnd = null;

    Deque<TreeNode> queue = new LinkedList<>();
    queue.offerLast(this);
    while (!queue.isEmpty()) {
      node = queue.pollFirst();
      buf.append(pre).append(node.val);
      pre = ",";
      if (node.left != null) {
        queue.offerLast(node.left);
        nextEnd = node.left;
      }
      if (node.right != null) {
        queue.offerLast(node.right);
        nextEnd = node.right;
      }
      if (node == currEnd) {
        buf.append(pre).append("#");
        currEnd = nextEnd;
      }
    }
    buf.append("]");
    return buf.toString();
  }

}
