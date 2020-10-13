package xyz.flysium.photon.algorithm.linkedlist.numer.medium;

import java.util.Stack;
import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 445. 两数相加 II
 * <p>
 * https://leetcode-cn.com/problems/add-two-numbers-ii/
 *
 * @author zeno
 */
public class T0445_AddTwoNumbersII_1 {

  //  给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
  //  你可以假设除了数字 0 之外，这两个数字都不会以零开头。
  //  进阶：
  //  如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
  static class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
      Stack<Integer> stack1 = new Stack<>();
      Stack<Integer> stack2 = new Stack<>();
      ListNode curr = l1;
      while (curr != null) {
        stack1.push(curr.val);
        curr = curr.next;
      }
      curr = l2;
      while (curr != null) {
        stack2.push(curr.val);
        curr = curr.next;
      }

      ListNode dummy = new ListNode(0);
      ListNode next = null;
      Integer p1 = stack1.pop();
      Integer p2 = stack2.pop();
      // carry or not
      int carryOver = 0;
      while (p1 != null || p2 != null) {
        int e = carryOver + (p1 == null ? 0 : p1) + (p2 == null ? 0 : p2);
        if (e >= 10) {
          e = e - 10;
          carryOver = 1;
        } else {
          carryOver = 0;
        }
        // add to head
        ListNode n = new ListNode(e);
        n.next = next;
        dummy.next = n;
        next = n;
        p1 = stack1.isEmpty() ? null : stack1.pop();
        p2 = stack2.isEmpty() ? null : stack2.pop();
      }
      if (carryOver == 1) {
        ListNode n = new ListNode(1);
        n.next = next;
        dummy.next = n;
      }
      return dummy.next;
    }

  }

}
