package xyz.flysium.photon.jianzhioffer.datastructure.easy;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 剑指 Offer 06. 从尾到头打印链表
 * <p>
 * https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof
 *
 * @author zeno
 */
public interface J0006_ReversePrint {

  // 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。

  // 0ms 100.00%
  class Solution {

    public int[] reversePrint(ListNode head) {
      ListNode curr = head;
      int len = 0;
      while (curr != null) {
        curr = curr.next;
        len++;
      }
      int[] ans = new int[len];
      int i = len - 1;
      curr = head;
      while (curr != null) {
        ans[i--] = curr.val;
        curr = curr.next;
      }
      return ans;
    }

//    public int[] reversePrint(ListNode head) {
//      ListNode curr = head;
//      Deque<Integer> stack = new LinkedList<>();
//      int len = 0;
//      while (curr != null) {
//        stack.push(curr.val);
//        curr = curr.next;
//        len++;
//      }
//      int[] ans = new int[len];
//      int i = 0;
//      while (!stack.isEmpty()) {
//        ans[i++] = stack.pop();
//      }
//      return ans;
//    }

  }

}
