package xyz.flysium.photon.algorithm.linkedlist.rotate.easy;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 206. 反转链表
 * <p>
 * https://leetcode-cn.com/problems/reverse-linked-list/
 *
 * @author zeno
 */
public class T0206_ReverseLinkedList {

  // 反转一个单链表。
  static class Solution {

    public ListNode reverseList(ListNode head) {
      ListNode prev = null;
      ListNode curr = head;
      while (curr != null) {
        ListNode tmp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = tmp;
      }
      return prev;
    }

//   public ListNode reverseList(ListNode head) {
//      if (head == null) {
//        return null;
//      }
//      if (head.next == null) {
//        return head;
//      }
//      Stack<ListNode> stack = new Stack<ListNode>();
//      ListNode curr = head;
//      ListNode prev = null;
//
//      while (curr != null) {
//        stack.push(curr);
//        prev = curr;
//        curr = curr.next;
//        prev.next = null;
//      }
//      ListNode ans = stack.pop();
//      curr = ans;
//      while (!stack.isEmpty()) {
//        ListNode node = stack.pop();
//        curr.next = node;
//        curr = node;
//      }
//      return ans;
//    }

  }

}
