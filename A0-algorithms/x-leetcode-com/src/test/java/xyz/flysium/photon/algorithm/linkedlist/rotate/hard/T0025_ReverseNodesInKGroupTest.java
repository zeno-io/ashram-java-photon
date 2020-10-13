package xyz.flysium.photon.algorithm.linkedlist.rotate.hard;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.LinkedListSupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0025_ReverseNodesInKGroupTest {

  @Test
  public void test() {
    T0025_ReverseNodesInKGroup.Solution solution = new T0025_ReverseNodesInKGroup.Solution();
    int[] actual = null;

    actual = LinkedListSupport.toArray(solution
      .reverseKGroup(LinkedListSupport.toLinkedList(ArraySupport.newArray("[1,2,3,4,5]")), 2));
    Assert.assertArrayEquals(ArraySupport.newArray("[2,1,4,3,5]"), actual);

  }

}
