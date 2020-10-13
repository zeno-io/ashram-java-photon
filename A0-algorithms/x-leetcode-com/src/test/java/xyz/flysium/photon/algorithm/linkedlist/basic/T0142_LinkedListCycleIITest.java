package xyz.flysium.photon.algorithm.linkedlist.basic;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.LinkedListSupport;
import xyz.flysium.photon.linkedlist.ListNode;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0142_LinkedListCycleIITest {

  @Test
  public void test() {
    T0142_LinkedListCycleII.Solution solution = new T0142_LinkedListCycleII.Solution();
    ListNode head = null;

    head = LinkedListSupport.toLinkedList(3, 2, 0, -4);
    LinkedListSupport.makeCycle(head, 1);

    ListNode node = solution.detectCycle(head);
    Assert.assertEquals(2, node.val);
  }

}
