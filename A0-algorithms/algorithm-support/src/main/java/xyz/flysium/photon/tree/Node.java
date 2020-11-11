package xyz.flysium.photon.tree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * TODO description
 *
 * @author zeno
 */
public class Node implements ITreeNode {

  public int val;
  public Node left;
  public Node right;
  // 可选
  public Node next;
  public Node parent;

  public Node() {
  }

  public Node(int val) {
    this.val = val;
  }

  public Node(int _val, Node _left, Node _right, Node _next) {
    val = _val;
    left = _left;
    right = _right;
    next = _next;
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
  public Node getLeft() {
    return left;
  }

  @Override
  public void setLeft(ITreeNode left) {
    this.left = (Node) left;
  }

  @Override
  public Node getRight() {
    return right;
  }

  @Override
  public void setRight(ITreeNode right) {
    this.right = (Node) right;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder("[");
    String pre = "";
    Node head = this;
    int cnt = 0;
    Node node = null;
    Node currEnd = this;
    Node nextEnd = null;

    Deque<Node> queue = new LinkedList<>();
    queue.offerLast(head);
    while (!queue.isEmpty()) {
      node = queue.pollFirst();
      if (node == head) {
        if (cnt == 1) {
          break;
        }
        cnt++;
      }
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
