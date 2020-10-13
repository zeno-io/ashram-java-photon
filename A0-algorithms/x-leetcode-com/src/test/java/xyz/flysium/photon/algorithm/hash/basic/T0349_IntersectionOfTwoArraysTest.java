package xyz.flysium.photon.algorithm.hash.basic;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0349_IntersectionOfTwoArraysTest {

  @Test
  public void test() {
    T0349_IntersectionOfTwoArrays.Solution solution = new T0349_IntersectionOfTwoArrays.Solution();
    int[] actuals = null;

    actuals = solution
      .intersection(ArraySupport.newArray("[1,2,2,1]"), ArraySupport.newArray("[2,2]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[2]"), actuals);

    actuals = solution
      .intersection(ArraySupport.newArray("[4,9,5]"), ArraySupport.newArray("[9,4,9,8,4]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[9,4]"), actuals);
  }

  @Test
  public void test1() {
    T0349_IntersectionOfTwoArrays_1.Solution solution = new T0349_IntersectionOfTwoArrays_1.Solution();
    int[] actuals = null;

    actuals = solution
      .intersection(ArraySupport.newArray("[1,2,2,1]"), ArraySupport.newArray("[2,2]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[2]"), actuals);

    actuals = solution
      .intersection(ArraySupport.newArray("[4,9,5]"), ArraySupport.newArray("[9,4,9,8,4]"));
    Assert.assertArrayEquals(ArraySupport.newArray("[9,4]"), actuals);
  }
}
