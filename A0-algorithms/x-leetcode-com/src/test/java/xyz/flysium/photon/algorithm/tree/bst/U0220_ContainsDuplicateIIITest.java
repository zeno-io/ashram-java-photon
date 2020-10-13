package xyz.flysium.photon.algorithm.tree.bst;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class U0220_ContainsDuplicateIIITest {

  @Test
  public void test() {
    U0220_ContainsDuplicateIII.Solution solution = new U0220_ContainsDuplicateIII.Solution();

    Assert.assertTrue(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[1,2,3,1]"), 3,
        0));

    Assert.assertTrue(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[1,0,1,1]"), 1,
        2));

    Assert.assertFalse(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[1,5,9,1,5,9]"), 2,
        3));

    Assert.assertFalse(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[2147483647,-1,2147483647]"), 1,
        2147483647));
  }

  @Test
  public void test1() {
    U0220_ContainsDuplicateIII_1.Solution solution = new U0220_ContainsDuplicateIII_1.Solution();

    Assert.assertTrue(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[1,2,3,1]"), 3,
        0));

    Assert.assertTrue(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[1,0,1,1]"), 1,
        2));

    Assert.assertFalse(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[1,5,9,1,5,9]"), 2,
        3));

    Assert.assertFalse(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[2147483647,-1,2147483647]"), 1,
        2147483647));
  }

  @Test
  public void test2() {
    U0220_ContainsDuplicateIII_1.Solution solution = new U0220_ContainsDuplicateIII_1.Solution();

    Assert.assertTrue(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[1,2,3,1]"), 3,
        0));

    Assert.assertTrue(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[1,0,1,1]"), 1,
        2));

    Assert.assertFalse(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[1,5,9,1,5,9]"), 2,
        3));

    Assert.assertFalse(
      solution.containsNearbyAlmostDuplicate(ArraySupport.newArray("[2147483647,-1,2147483647]"), 1,
        2147483647));
  }

}
