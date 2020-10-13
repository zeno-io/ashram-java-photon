package xyz.flysium.photon.algorithm.linkedlist.numer.medium;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 445. 两数相加 II
 * <p>
 * https://leetcode-cn.com/problems/add-two-numbers-ii/
 *
 * @author zeno
 */
public class T0445_AddTwoNumbersII {

  //  给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
  //  你可以假设除了数字 0 之外，这两个数字都不会以零开头。
  //  进阶：
  //  如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
  static class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
      int len1 = length(l1);
      int len2 = length(l2);
      ListNode dummy = new ListNode(0);
      if (len1 >= len2) {
        add(l1, l2, dummy, len1, len2);
        dummy.next = l1;
      } else {
        add(l2, l1, dummy, len2, len1);
        dummy.next = l2;
      }
      return dummy.val == 0 ? dummy.next : dummy;
    }

    private void add(ListNode n1, ListNode n2, ListNode n1Prev, int len1, int len2) {
      if (n1 == null) {
        return;
      }
      if (len1 > len2) {
        // do nothing, just next
        add(n1.next, n2, n1, len1 - 1, len2);
      } else {
        // n1.next = n1.next + n2.next
        add(n1.next, n2.next, n1, len1 - 1, len2 - 1);
        // n1 = n1 + n2
        n1.val += n2.val;
      }
      // carry over
      if (n1.val >= 10) {
        n1.val = n1.val - 10;
        n1Prev.val++;
      }
    }

    private int length(ListNode head) {
      int len = 0;
      ListNode curr = head;
      while (curr != null) {
        len++;
        curr = curr.next;
      }
      return len;
    }

  }

}
