package xyz.flysium.photon.algorithm.linkedlist.numer.medium;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 2. 两数相加
 * <p>
 * https://leetcode-cn.com/problems/add-two-numbers/
 *
 * @author zeno
 */
public class T0002_AddTwoNumbers {

  //给出两个非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，并且它们的每个节点只能存储一位数字。
  //如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
  //您可以假设除了数字 0 之外，这两个数都不会以 0开头。
  //示例：
  //输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
  //输出：7 -> 0 -> 8
  //原因：342 + 465 = 807
  static class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
      ListNode dummy = new ListNode(0);
      ListNode prev = dummy;
      ListNode p1 = l1;
      ListNode p2 = l2;
      // carry or not
      int carryOver = 0;
      while (p1 != null || p2 != null) {
        int e = carryOver + (p1 == null ? 0 : p1.val) + (p2 == null ? 0 : p2.val);
        if (e >= 10) {
          e = e - 10;
          carryOver = 1;
        } else {
          carryOver = 0;
        }
        // add to tail
        ListNode n = new ListNode(e);
        prev.next = n;
        prev = n;
        if (p1 != null) {
          p1 = p1.next;
        }
        if (p2 != null) {
          p2 = p2.next;
        }
      }
      if (carryOver == 1) {
        prev.next = new ListNode(1);
      }
      return dummy.next;
    }

//  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//      int sum = sum(l1) + sum(l2);
//      ListNode dummy = new ListNode(0);
//      ListNode prev = dummy;
//      if (sum == 0) {
//        return dummy;
//      } else {
//        while (sum > 0) {
//          int s = sum / 10;
//          int e = sum - 10 * s;
//          ListNode n = new ListNode(e);
//          prev.next = n;
//          prev = n;
//          sum = s;
//        }
//      }
//
//      return dummy.next;
//    }
//    private int sum(ListNode l) {
//      int sum = 0;
//      int degree = 1;
//      ListNode curr = l;
//      while (curr != null) {
//        sum = degree * curr.val + sum;
//        curr = curr.next;
//        degree *= 10;
//      }
//      return sum;
//    }

  }

}
