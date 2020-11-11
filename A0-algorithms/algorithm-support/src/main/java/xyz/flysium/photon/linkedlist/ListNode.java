package xyz.flysium.photon.linkedlist;

import xyz.flysium.photon.LinkedListSupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class ListNode {

  public int val;
  public ListNode next;

  public ListNode(int val) {
    this.val = val;
  }

  public ListNode(int val, ListNode next) {
    this.val = val;
    this.next = next;
  }

  @Override
  public String toString() {
    ListNode firstLoopNode = LinkedListSupport.getFirstLoopNode(this);
    if (firstLoopNode != null) {
      return "[" + this.val + "] is loop at[" + firstLoopNode.val + "]";
    }
    ListNode c = this;
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

}
