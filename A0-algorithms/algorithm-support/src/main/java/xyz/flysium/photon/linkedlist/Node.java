package xyz.flysium.photon.linkedlist;

/**
 * TODO description
 *
 * @author zeno
 */
public class Node {

  public int val;
  public Node prev;
  public Node next;
  // 可选
  public Node child;
  // 可选
  public Node random;

  public Node(int val) {
    this.val = val;
  }

  public Node(int val, Node next) {
    this.val = val;
    this.next = next;
  }

  @Override
  public String toString() {
    Node c = this;
    StringBuilder buf = new StringBuilder("[");
    String pre = "";
    while (c != null) {
      buf.append(pre).append(c.val);
      pre = ",";
      c = c.next;
    }
    buf.append("]");
    return buf.toString();
  }

};
