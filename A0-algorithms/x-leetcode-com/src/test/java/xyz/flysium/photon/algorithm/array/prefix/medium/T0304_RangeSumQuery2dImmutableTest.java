package xyz.flysium.photon.algorithm.array.prefix.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.algorithm.array.prefix.medium.T0304_RangeSumQuery2dImmutable.NumMatrix;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0304_RangeSumQuery2dImmutableTest {

  @Test
  public void test() {
    int[][] nums = ArraySupport.newTwoDimensionalArray(
      "[[3,0,1,4,2],[5,6,3,2,1],[1,2,0,1,5],[4,1,0,1,7],[1,0,3,0,5]]");
    T0304_RangeSumQuery2dImmutable.NumMatrix numArray = new NumMatrix(
      nums);
    Assert.assertEquals(8, numArray.sumRegion(2, 1, 4, 3));
    Assert.assertEquals(11, numArray.sumRegion(1, 1, 2, 2));
    Assert.assertEquals(12, numArray.sumRegion(1, 2, 2, 4));
  }

}
