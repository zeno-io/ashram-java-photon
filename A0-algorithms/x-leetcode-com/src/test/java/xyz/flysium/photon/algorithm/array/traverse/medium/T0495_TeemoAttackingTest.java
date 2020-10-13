package xyz.flysium.photon.algorithm.array.traverse.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0495_TeemoAttackingTest {

  @Test
  public void test1() {
    T0495_TeemoAttacking.Solution solution = new T0495_TeemoAttacking.Solution();
    Assert.assertEquals(4, solution.findPoisonedDuration(ArraySupport.newArray("[1, 4]"), 2));
    Assert.assertEquals(3, solution.findPoisonedDuration(ArraySupport.newArray("[1, 2]"), 2));
    Assert.assertEquals(5, solution.findPoisonedDuration(ArraySupport.newArray("[1, 2, 5]"), 2));
    Assert.assertEquals(5, solution.findPoisonedDuration(ArraySupport.newArray("[1, 2, 4]"), 2));
    Assert.assertEquals(4, solution.findPoisonedDuration(ArraySupport.newArray("[1, 2, 3]"), 2));
    Assert.assertEquals(19999999,
      solution.findPoisonedDuration(ArraySupport.newArray("[1, 9000000, 10000000]"), 10000000));
  }

}
