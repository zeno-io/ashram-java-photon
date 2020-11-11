package xyz.flysium.photon.linkedlist;

/**
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2。
 * <p>
 * 请实现一个函数，如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null
 * <p>
 * 【要求】 如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T22_FindFirstIntersectNode {

  public static void main(String[] args) {
    T22_FindFirstIntersectNode solution = new T22_FindFirstIntersectNode();
    // 1->2->3->4->5->6->7->null
    ListNode head1 = new ListNode(1);
    head1.next = new ListNode(2);
    head1.next.next = new ListNode(3);
    head1.next.next.next = new ListNode(4);
    head1.next.next.next.next = new ListNode(5);
    head1.next.next.next.next.next = new ListNode(6);
    head1.next.next.next.next.next.next = new ListNode(7);

    // 0->9->8->6->7->null
    ListNode head2 = new ListNode(0);
    head2.next = new ListNode(9);
    head2.next.next = new ListNode(8);
    head2.next.next.next = head1.next.next.next.next.next; // 8->6
    System.out.println(solution.getIntersectNode(head1, head2).val == 6);

    // 1->2->3->4->5->6->7->4...
    head1 = new ListNode(1);
    head1.next = new ListNode(2);
    head1.next.next = new ListNode(3);
    head1.next.next.next = new ListNode(4);
    head1.next.next.next.next = new ListNode(5);
    head1.next.next.next.next.next = new ListNode(6);
    head1.next.next.next.next.next.next = new ListNode(7);
    head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

    // 0->9->8->2...
    head2 = new ListNode(0);
    head2.next = new ListNode(9);
    head2.next.next = new ListNode(8);
    head2.next.next.next = head1.next; // 8->2
    System.out.println(solution.getIntersectNode(head1, head2).val == 2);

    // 0->9->8->6->4->5->6..
    head2 = new ListNode(0);
    head2.next = new ListNode(9);
    head2.next.next = new ListNode(8);
    head2.next.next.next = head1.next.next.next.next.next; // 8->6
    System.out.println(solution.getIntersectNode(head1, head2).val == 4);
  }

  // 只可能出现两种情况：不相交，如果相交，必然有共同的部分或环
  private ListNode getIntersectNode(ListNode head1, ListNode head2) {
    if (head1 == null || head2 == null) {
      return null;
    }
    // find the first loop node
    ListNode loop1 = getFirstLoopNode(head1);
    ListNode loop2 = getFirstLoopNode(head2);
    // (1) not loop, not intersect
    if (loop1 == null && loop2 == null) {
      return getTheIntersectNode(head1, head2, null);
    }
    // (2) both has a loop, intersect or not ?
    if (loop1 != null && loop2 != null) {
      // (2-1) loop first node are the same
      if (loop1 == loop2) {
        // length is not correct now
        return getTheIntersectNode(head1, head2, loop1);
      }
      // (2-2) loop first node are different
      ListNode cur1 = loop1.next;
      while (cur1 != loop1) {
        if (cur1 == loop2) {
          return loop1;
        }
        cur1 = cur1.next;
      }
    }
    return null;
  }

  // find the first loop node
  private ListNode getFirstLoopNode(ListNode head) {
    boolean hasLoop = false;
    ListNode curr = head;
    ListNode fast = head;
    while (curr != null && fast.next != null && fast.next.next != null) {
      curr = curr.next;
      fast = fast.next.next;
      // find loop
      if (curr == fast) {
        hasLoop = true;
        break;
      }
    }
    ListNode firstLoopNode = null;
    if (hasLoop) {
      curr = head;
      while (curr != null) {
        curr = curr.next;
        fast = fast.next;
        if (curr == fast) {
          firstLoopNode = curr;
          break;
        }
      }
    }
    return firstLoopNode;
  }

  private ListNode getTheIntersectNode(ListNode head1, ListNode head2, ListNode end) {
    ListNode p1 = head1;
    ListNode p2 = head2;
    // long go first
    int n = 0;
    while (p1 != end) {
      p1 = p1.next;
      n++;
    }
    while (p2 != end) {
      p2 = p2.next;
      n--;
    }
    // go ahead n steps
    p1 = head1;
    p2 = head2;
    if (n > 0) {
      p1 = ahead(p1, n);
    } else if (n < 0) {
      p2 = ahead(p2, -n);
    }
    // when in the same node, it's the intersect node
    while (p1 != p2) {
      p1 = p1.next;
      p2 = p2.next;
    }
    return p1;
  }

  // go ahead n steps
  private ListNode ahead(ListNode node, int len) {
    if (len <= 0) {
      return node;
    }
    int i = 0;
    while (i < len) {
      node = node.next;
      i++;
    }
    return node;
  }


}
