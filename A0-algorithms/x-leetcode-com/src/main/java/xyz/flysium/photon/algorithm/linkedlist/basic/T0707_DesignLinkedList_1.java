package xyz.flysium.photon.algorithm.linkedlist.basic;

/**
 * 707. 设计链表
 * <p>
 * https://leetcode-cn.com/problems/design-linked-list/
 *
 * @author zeno
 */
public interface T0707_DesignLinkedList_1 {

// 设计链表的实现。您可以选择使用单链表或双链表。单链表中的节点应该具有两个属性：val 和 next。val 是当前节点的值，next 是指向下一个节点的指针/引用。如果要使用双向链表，则还需要一个属性 prev 以指示链表中的上一个节点。假设链表中的所有节点都是 0-index 的。
//
// 在链表类中实现这些功能：
//
//    get(index)：获取链表中第 index 个节点的值。如果索引无效，则返回-1。
//    addAtHead(val)：在链表的第一个元素之前添加一个值为 val 的节点。插入后，新节点将成为链表的第一个节点。
//    addAtTail(val)：将值为 val 的节点追加到链表的最后一个元素。
//    addAtIndex(index,val)：在链表中的第 index 个节点之前添加值为 val  的节点。如果 index 等于链表的长度，则该节点将附加到链表的末尾。如果 index 大于链表长度，则不会插入节点。如果index小于0，则在头部插入节点。
//    deleteAtIndex(index)：如果索引 index 有效，则删除链表中的第 index 个节点。

  class MyNode {

    final int val;
    MyNode next;

    MyNode(int val) {
      this.val = val;
    }

  }

  class MyLinkedList {

    MyNode head;
    int size = 0;

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {
      head = new MyNode(0);
      head.next = null;
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
      if (index < 0 || index >= size) {
        return -1;
      }
      MyNode curr = indexOf(index);
      if (curr == null) {
        return -1;
      }
      return curr.val;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the
     * new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
      MyNode pred = head;
      MyNode succ = head.next;

      MyNode toAdd = new MyNode(val);
      toAdd.next = succ;
      pred.next = toAdd;
      size++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
      if (head.next == null) {
        addAtHead(val);
        return;
      }
      MyNode pred = indexOf(size - 1);
      if (pred == null) {
        return;
      }
      MyNode succ = pred.next;

      MyNode toAdd = new MyNode(val);
      toAdd.next = succ;
      pred.next = toAdd;
      size++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the
     * length of linked list, the node will be appended to the end of linked list. If index is
     * greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
      if (index < 0 || index > size) {
        return;
      }
      if (index == 0) {
        addAtHead(val);
        return;
      }
      if (index == size) {
        addAtTail(val);
        return;
      }
      MyNode pred = indexOf(index - 1);
      if (pred == null) {
        return;
      }
      MyNode succ = pred.next;

      MyNode toAdd = new MyNode(val);
      toAdd.next = succ;
      pred.next = toAdd;
      size++;
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
      if (index < 0 || index >= size) {
        return;
      }
      MyNode pred = indexOf(index - 1);
      if (pred == null) {
        return;
      }
      MyNode toDeleted = pred.next;
      if (toDeleted == null) {
        return;
      }
      pred.next = toDeleted.next;
      size--;
    }

    private MyNode indexOf(int n) {
      if (n < 0) {
        return head;
      }
      int i = 0;
      MyNode curr = head.next;
      while (i < n && curr != null) {
        i++;
        curr = curr.next;
      }
      if (i == n) {
        return curr;
      }
      return null;
    }

    @Override
    public String toString() {
      MyNode c = head.next;
      StringBuilder buf = new StringBuilder("[");
      String pre = "";
      while (c != null) {
        buf.append(pre).append(c.val);
        pre = ",";
        c = c.next;
      }
      buf.append("]");
      return buf.toString();
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
