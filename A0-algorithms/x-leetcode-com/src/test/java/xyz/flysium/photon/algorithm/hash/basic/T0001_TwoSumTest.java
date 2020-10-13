package xyz.flysium.photon.algorithm.hash.basic;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0001_TwoSumTest {

  @Test
  public void test() {
    T0001_TwoSum.Solution solution = new T0001_TwoSum.Solution();

    Assert.assertArrayEquals(ArraySupport.newArray("[1,2]"),
      solution.twoSum(ArraySupport.newArray("[3,2,4]"), 6));
    Assert.assertArrayEquals(ArraySupport.newArray("[0,1]"),
      solution.twoSum(ArraySupport.newArray("[2, 7, 11, 15]"), 9));
  }

}
