package xyz.flysium.photon.jianzhioffer.twopointers.easy;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 剑指 Offer 25. 合并两个排序的链表
 * <p>
 * https://leetcode-cn.com/problems/he-bing-liang-ge-pai-xu-de-lian-biao-lcof/
 *
 * @author zeno
 */
public class J0025_MergeTwoLists {

//输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。
//
// 示例1：
//
// 输入：1->2->4, 1->3->4
//输出：1->1->2->3->4->4
//
// 限制：
//
// 0 <= 链表长度 <= 1000
//
// 注意：本题与主站 21 题相同：https://leetcode-cn.com/problems/merge-two-sorted-lists/
// Related Topics 分治算法
// 👍 54 👎 0


  public static void main(String[] args) {
    Solution solution = new J0025_MergeTwoLists().new Solution();

  }

  // 执行耗时:1 ms,击败了99.41% 的Java用户
//leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode(int
   * x) { val = x; } }
   */
  class Solution {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
      ListNode dummy = new ListNode(0);
      ListNode prev = dummy;
      ListNode c1 = l1;
      ListNode c2 = l2;
      while (c1 != null && c2 != null) {
        if (c1.val < c2.val) {
          prev.next = c1;
          prev = c1;
          c1 = c1.next;
        } else {
          prev.next = c2;
          prev = c2;
          c2 = c2.next;
        }
      }
      while (c1 != null) {
        prev.next = c1;
        prev = c1;
        c1 = c1.next;
      }
      while (c2 != null) {
        prev.next = c2;
        prev = c2;
        c2 = c2.next;
      }
      return dummy.next;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
