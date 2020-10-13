package xyz.flysium.photon.algorithm.linkedlist.rotate.medium;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 92. 反转链表 II
 * <p>
 * https://leetcode-cn.com/problems/reverse-linked-list-ii/
 *
 * @author zeno
 */
public class T0092_ReverseLinkedListII {

  // 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
  //  1 ≤ m ≤ n ≤ 链表长度。
  static class Solution {

    public ListNode reverseBetween(ListNode head, int m, int n) {
      ListNode prev = null;
      ListNode curr = head;
      for (int i = 1; i < m; i++) {
        prev = curr;
        curr = curr.next;
      }
      if (m < n) {
        ListNode rPrev = null;
        ListNode rCurr = curr;
        ListNode rTmp = null;
        int i = 0;
        while (rCurr != null && i < (n - m + 1)) {
          rTmp = rCurr.next;
          rCurr.next = rPrev;
          rPrev = rCurr;
          rCurr = rTmp;
          i++;
        }
        if (m == 1) {
          head.next = rCurr;
          return rPrev;
        }
        prev.next = rPrev;
        curr.next = rCurr;
      }
      return head;
    }

  }


}
