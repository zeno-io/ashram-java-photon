package xyz.flysium.photon.algorithm.linkedlist.basic;

/**
 * 707. 设计链表
 * <p>
 * https://leetcode-cn.com/problems/design-linked-list/
 *
 * @author zeno
 */
public interface T0707_DesignLinkedList {

// 设计链表的实现。您可以选择使用单链表或双链表。单链表中的节点应该具有两个属性：val 和 next。val 是当前节点的值，next 是指向下一个节点的指针/引用。如果要使用双向链表，则还需要一个属性 prev 以指示链表中的上一个节点。假设链表中的所有节点都是 0-index 的。
//
// 在链表类中实现这些功能：
//
//    get(index)：获取链表中第 index 个节点的值。如果索引无效，则返回-1。
//    addAtHead(val)：在链表的第一个元素之前添加一个值为 val 的节点。插入后，新节点将成为链表的第一个节点。
//    addAtTail(val)：将值为 val 的节点追加到链表的最后一个元素。
//    addAtIndex(index,val)：在链表中的第 index 个节点之前添加值为 val  的节点。如果 index 等于链表的长度，则该节点将附加到链表的末尾。如果 index 大于链表长度，则不会插入节点。如果index小于0，则在头部插入节点。
//    deleteAtIndex(index)：如果索引 index 有效，则删除链表中的第 index 个节点。

  class MyListNode {

    int val;
    MyListNode next;
    MyListNode prev;

    MyListNode(int x) {
      val = x;
    }
  }

  class MyLinkedList {

    int size;
    // sentinel nodes as pseudo-head and pseudo-tail
    MyListNode head, tail;

    public MyLinkedList() {
      size = 0;
      head = new MyListNode(0);
      tail = new MyListNode(0);
      head.next = tail;
      tail.prev = head;
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
      // if index is invalid
      if (index < 0 || index >= size) {
        return -1;
      }

      // choose the fastest way: to move from the head
      // or to move from the tail
      MyListNode curr = head;
      if (index + 1 < size - index) {
        for (int i = 0; i < index + 1; ++i) {
          curr = curr.next;
        }
      } else {
        curr = tail;
        for (int i = 0; i < size - index; ++i) {
          curr = curr.prev;
        }
      }

      return curr.val;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the
     * new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
      MyListNode pred = head, succ = head.next;

      ++size;
      MyListNode toAdd = new MyListNode(val);
      toAdd.prev = pred;
      toAdd.next = succ;
      pred.next = toAdd;
      succ.prev = toAdd;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
      MyListNode succ = tail, pred = tail.prev;

      ++size;
      MyListNode toAdd = new MyListNode(val);
      toAdd.prev = pred;
      toAdd.next = succ;
      pred.next = toAdd;
      succ.prev = toAdd;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the
     * length of linked list, the node will be appended to the end of linked list. If index is
     * greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
      // If index is greater than the length,
      // the node will not be inserted.
      if (index > size) {
        return;
      }

      // [so weird] If index is negative,
      // the node will be inserted at the head of the list.
      if (index < 0) {
        index = 0;
      }

      // find predecessor and successor of the node to be added
      MyListNode pred, succ;
      if (index < size - index) {
        pred = head;
        for (int i = 0; i < index; ++i) {
          pred = pred.next;
        }
        succ = pred.next;
      } else {
        succ = tail;
        for (int i = 0; i < size - index; ++i) {
          succ = succ.prev;
        }
        pred = succ.prev;
      }

      // insertion itself
      ++size;
      MyListNode toAdd = new MyListNode(val);
      toAdd.prev = pred;
      toAdd.next = succ;
      pred.next = toAdd;
      succ.prev = toAdd;
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
      // if the index is invalid, do nothing
      if (index < 0 || index >= size) {
        return;
      }

      // find predecessor and successor of the node to be deleted
      MyListNode pred, succ;
      if (index < size - index) {
        pred = head;
        for (int i = 0; i < index; ++i) {
          pred = pred.next;
        }
        succ = pred.next.next;
      } else {
        succ = tail;
        for (int i = 0; i < size - index - 1; ++i) {
          succ = succ.prev;
        }
        pred = succ.prev.prev;
      }

      // delete pred.next
      --size;
      pred.next = succ;
      succ.prev = pred;
    }
  }

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
}
