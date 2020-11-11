package xyz.flysium.photon.algorithm.hash.basic;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.algorithm.hash.basic.beginner.T0350_IntersectionOfTwoArraysII;
import xyz.flysium.photon.algorithm.hash.basic.beginner.T0350_IntersectionOfTwoArraysII_1;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0350_IntersectionOfTwoArraysIITest {

  @Test
  public void test() {
    T0350_IntersectionOfTwoArraysII.Solution solution = new T0350_IntersectionOfTwoArraysII.Solution();
    int[] actuals = null;

    actuals = solution
      .intersect(ArraySupport.newArray("[1,2,2,1]"), ArraySupport.newArray("[2,2]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[2,2]"),
      actuals);

    actuals = solution.intersect(ArraySupport.newArray("[1,2,2,1]"), ArraySupport.newArray("[2]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[2]"),
      actuals);

    actuals = solution
      .intersect(ArraySupport.newArray("[4,9,5]"), ArraySupport.newArray("[9,4,9,8,4]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[4,9]"),
      actuals);

    actuals = solution.intersect(ArraySupport.newArray("[-2147483648,1,2,3]"),
      ArraySupport.newArray("[1,-2147483648,-2147483648]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[-2147483648,1]"),
      actuals);
  }

  @Test
  public void test1() {
    T0350_IntersectionOfTwoArraysII_1.Solution solution = new T0350_IntersectionOfTwoArraysII_1.Solution();
    int[] actuals = null;

    actuals = solution
      .intersect(ArraySupport.newArray("[1,2,2,1]"), ArraySupport.newArray("[2,2]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[2,2]"),
      actuals);

    actuals = solution.intersect(ArraySupport.newArray("[1,2,2,1]"), ArraySupport.newArray("[2]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[2]"),
      actuals);

    actuals = solution
      .intersect(ArraySupport.newArray("[4,9,5]"), ArraySupport.newArray("[9,4,9,8,4]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[4,9]"),
      actuals);

    actuals = solution.intersect(ArraySupport.newArray("[-2147483648,1,2,3]"),
      ArraySupport.newArray("[1,-2147483648,-2147483648]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[-2147483648,1]"),
      actuals);
  }


}
