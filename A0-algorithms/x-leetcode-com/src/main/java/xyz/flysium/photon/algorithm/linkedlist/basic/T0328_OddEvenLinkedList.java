package xyz.flysium.photon.algorithm.linkedlist.basic;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 328. 奇偶链表
 * <p>
 * https://leetcode-cn.com/problems/odd-even-linked-list/
 *
 * @author zeno
 */
public interface T0328_OddEvenLinkedList {

  //  给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。
  //  请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。
  //
  //  请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数。
  class Solution {

    public ListNode oddEvenList(ListNode head) {
      if (head == null) {
        return null;
      }
      if (head.next == null) {
        return head;
      }
      ListNode oddDummy = new ListNode(0);
      ListNode evenDummy = new ListNode(0);
      ListNode odd = oddDummy;
      ListNode even = evenDummy;
      ListNode curr = head;
      ListNode next = null;

      int i = 1;
      while (curr != null) {
        next = curr.next;
        curr.next = null;
        // odd
        if ((i & (~i + 1)) == 1) {
          odd.next = curr;
          odd = curr;
        } else {
          even.next = curr;
          even = curr;
        }
        curr = next;
        i++;
      }
      odd.next = evenDummy.next;

      return oddDummy.next;
    }

  }

}
