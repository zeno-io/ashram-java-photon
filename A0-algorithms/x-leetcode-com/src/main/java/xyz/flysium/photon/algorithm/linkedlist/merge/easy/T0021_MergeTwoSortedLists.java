package xyz.flysium.photon.algorithm.linkedlist.merge.easy;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 21. 合并两个有序链表
 * <p>
 * https://leetcode-cn.com/problems/merge-two-sorted-lists/
 *
 * @author zeno
 */
public class T0021_MergeTwoSortedLists {

  // 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
  static class Solution {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
      ListNode dummy = new ListNode(0);
      ListNode prev = dummy;
      ListNode p1 = l1, p2 = l2;
      boolean g1, g2;
      ListNode tmp = null;
      while (p1 != null || p2 != null) {
        g1 = p2 == null || (p1 != null && p1.val <= p2.val);
        g2 = p1 == null || (p2 != null && p2.val <= p1.val);
//        if (p1 == null) {
//          g2 = true;
//        } else if (p2 == null) {
//          g1 = true;
//        } else {
//          if (p1.val < p2.val) {
//            g1 = true;
//          } else if (p2.val < p1.val) {
//            g2 = true;
//          } else {
//            g1 = true;
//            g2 = true;
//          }
//        }
        if (g1) {
          tmp = new ListNode(p1.val);
          prev.next = tmp;
          prev = tmp;
          p1 = p1.next;
        }
        if (g2) {
          tmp = new ListNode(p2.val);
          prev.next = tmp;
          prev = tmp;
          p2 = p2.next;
        }
      }

      return dummy.next;
    }

  }

}
