package xyz.flysium.photon.algorithm.linkedlist.rotate.hard;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 25. K 个一组翻转链表
 * <p>
 * https://leetcode-cn.com/problems/reverse-nodes-in-k-group/
 *
 * @author zeno
 */
public class T0025_ReverseNodesInKGroup {

  //  给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
  //  k 是一个正整数，它的值小于或等于链表的长度。
  //  如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
  //  你的算法只能使用常数的额外空间。
  //  你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
  static class Solution {

    public ListNode reverseKGroup(ListNode head, int k) {
      ListNode ans = head;
      ListNode curr = head;
      int length = 0;
      while (curr != null) {
        length++;
        curr = curr.next;
      }

      ListNode originRStart = null;
      ListNode lastEnd = null;
      ListNode rPrev = null;
      ListNode rTmp = null;
      int i = 0;
      curr = head;
      for (int f = 0; f < length / k; f++) {
        rPrev = originRStart;
        // save last group end, and reverseKGroup end ( originRStart )
        lastEnd = originRStart;
        if (lastEnd != null) {
          lastEnd.next = null;
        }
        originRStart = curr;
        i = 0;
        while (curr != null && i < k) {
          rTmp = curr.next;
          curr.next = rPrev;
          rPrev = curr;
          curr = rTmp;
          i++;
        }
        // first get reverseKGroup start
        if (lastEnd == null) {
          ans = rPrev;
        } else {
          // last group end -> reverseKGroup start
          lastEnd.next = rPrev;
        }
        // reverseKGroup end ( originRStart ) -> next group
        if (originRStart != null) {
          originRStart.next = curr;
        }
      }
      return ans;
    }

  }

}
