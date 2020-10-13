package xyz.flysium.photon.algorithm.array.move.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0665_NonDecreasingArrayTest {

  @Test
  public void test() {
    T0665_NonDecreasingArray.Solution solution = new T0665_NonDecreasingArray.Solution();
    // (min + ?) -> ... -> min -> ... -> (min + ?)
    Assert.assertFalse(solution.checkPossibility(ArraySupport.newArray("[4, 2, 1]")));
    Assert.assertFalse(solution.checkPossibility(ArraySupport.newArray("[4, 2, 1]")));
    Assert.assertFalse(solution.checkPossibility(ArraySupport.newArray("[3, 4, 2, 3]")));
    // (min + ?) -> ... -> min
    Assert.assertTrue(solution.checkPossibility(ArraySupport.newArray("[4, 2, 3]")));
    Assert.assertFalse(solution.checkPossibility(ArraySupport.newArray("[4, 2, 1]")));
    // min -> ... -> (min + ?)
    Assert.assertTrue(solution.checkPossibility(ArraySupport.newArray("[1, 2, 3]")));
    Assert
      .assertFalse(solution.checkPossibility(ArraySupport.newArray("[1, 2, 1, 1, 1, 4, 5, 1, 7]")));
    // min -> ... -> min
    Assert.assertFalse(
      solution.checkPossibility(ArraySupport.newArray("[2, 2, 2, 3, 4, 5, 2, 2, 2, 3, 2, 2]")));
    Assert.assertFalse(
      solution.checkPossibility(ArraySupport.newArray("[2, 2, 2, 5, 2, 2, 2, 2, 3, 2, 2]")));
    Assert.assertTrue(
      solution.checkPossibility(ArraySupport.newArray("[2, 2, 2, 5, 2, 2, 2, 2, 2, 2, 2]")));
  }
}
