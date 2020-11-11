package xyz.flysium.photon.jianzhioffer.twopointers.easy;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 剑指 Offer 18. 删除链表的节点
 * <p>
 * https://leetcode-cn.com/problems/shan-chu-lian-biao-de-jie-dian-lcof/
 *
 * @author zeno
 */
public class J0018_DeleteNode {

//给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
//
// 返回删除后的链表的头节点。
//
// 注意：此题对比原题有改动
//
// 示例 1:
//
// 输入: head = [4,5,1,9], val = 5
//输出: [4,1,9]
//解释: 给定你链表中值为5的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
//
//
// 示例 2:
//
// 输入: head = [4,5,1,9], val = 1
//输出: [4,5,9]
//解释: 给定你链表中值为1的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
//
//
//
//
// 说明：
//
//
// 题目保证链表中节点的值互不相同
// 若使用 C 或 C++ 语言，你不需要 free 或 delete 被删除的节点
//
// Related Topics 链表
// 👍 58 👎 0


  public static void main(String[] args) {
    Solution solution = new J0018_DeleteNode().new Solution();

  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户
//leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode(int
   * x) { val = x; } }
   */
  class Solution {

    public ListNode deleteNode(ListNode head, int val) {
      ListNode dummy = new ListNode(0);
      dummy.next = head;
      ListNode prev = dummy;
      ListNode curr = head;

      while (curr != null) {
        if (curr.val == val) {
          prev.next = curr.next;
        }
        prev = curr;
        curr = curr.next;
      }

      return dummy.next;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
