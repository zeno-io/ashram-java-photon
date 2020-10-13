package xyz.flysium.photon.algorithm.linkedlist.basic;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 160. 相交链表
 * <p>
 * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 *
 * @author zeno
 */
public interface T0160_IntersectionOfTwoLinkedLists {

  // 编写一个程序，找到两个单链表相交的起始节点。
  // 可假定整个链表结构中没有循环。
  public class Solution {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
      final int lenA = length(headA);
      final int lenB = length(headB);
      ListNode nLong = null;
      ListNode nShort = null;
      int lenIncr = 0;
      if (lenA > lenB) {
        nLong = headA;
        nShort = headB;
        lenIncr = lenA - lenB;
      } else {
        nLong = headB;
        nShort = headA;
        lenIncr = lenB - lenA;
      }
      if (lenIncr > 0) {
        for (int i = 0; i < lenIncr; i++) {
          nLong = nLong.next;
        }
      }
      while (nLong != null && nShort != null && nLong != nShort) {
        nLong = nLong.next;
        nShort = nShort.next;
      }
      return nLong;
    }

    public int length(ListNode head) {
      int length = 0;
      ListNode p = head;
      while (p != null) {
        p = p.next;
        length++;
      }
      return length;
    }

  }

}
