package xyz.flysium.photon.algorithm.array.prefix.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0303_RangeSumQueryImmutableTest {


  @Test
  public void test() {
    int[] nums = ArraySupport.newArray("[-2,0,3,-5,2,-1]");
    T0303_RangeSumQueryImmutable.NumArray numArray = new T0303_RangeSumQueryImmutable.NumArray(
      nums);
    Assert.assertEquals(1, numArray.sumRange(0, 2));
    Assert.assertEquals(-1, numArray.sumRange(2, 5));
    Assert.assertEquals(-3, numArray.sumRange(0, 5));
  }

}
