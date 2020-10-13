package xyz.flysium.photon.algorithm.linkedlist.basic;

import xyz.flysium.photon.linkedlist.ListNode;

/**
 * 142. 环形链表II
 * <p>
 * https://leetcode-cn.com/problems/linked-list-cycle-ii/
 *
 * @author zeno
 */
public interface T0142_LinkedListCycleII {

  // 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
  //
  // 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
  //
  // 说明：不允许修改给定的链表。

  public class Solution {

    //假设存在环
    //1. 设从 head 走 a 步就可以走到环的入口， 环的长度 为 b
    //2. 如果存在环，快指针每次走 2步，慢指针每次走 1步，快慢指针一定在环中相遇。
    //   此时，假设慢指针在环中走了 x 步，快指针转了 m 圈：
    //      慢指针走了 a + x 步
    //      快指针走了 a + x + mb 步，又因为快指针是慢指针的2倍速
    //      因此  2 * ( a + x) = a + x + mb
    //  推导得到   a = mb - x
    //
    //3. 那么我们此时，可以在 head 开始一个新的指针 detection 与快指针一起，再走 a 步 (每次走 1步) ，就可以在环的入口相遇，理由：
    //      detection指针走了 a 步, 处于入口的位置
    //      快指针走了 a + x + mb + a = a + x + mb + mb - x = a + 2mb,  刚好走完了整整 2m 圈环，正好处于入口的位置
    public ListNode detectCycle(ListNode head) {
      if (head == null) {
        return null;
      }
      ListNode slow = head;
      ListNode fast = head;
      ListNode direction = head;
      while (slow != null && fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        // 存在环
        if (slow == fast) {
          // 找到环的入口
          while (direction != fast) {
            fast = fast.next;
            direction = direction.next;
          }
          return direction;
        }
      }
      return null;
    }

  }

}
