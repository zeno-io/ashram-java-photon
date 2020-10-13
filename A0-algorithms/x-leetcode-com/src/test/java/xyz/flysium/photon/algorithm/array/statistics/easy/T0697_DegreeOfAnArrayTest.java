package xyz.flysium.photon.algorithm.array.statistics.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0697_DegreeOfAnArrayTest {

  @Test
  public void test() {
    T0697_DegreeOfAnArray.Solution solution = new T0697_DegreeOfAnArray.Solution();
    Assert.assertEquals(2, solution.findShortestSubArray(ArraySupport.newArray("[1, 2, 2, 3, 1]")));
    Assert.assertEquals(6,
      solution.findShortestSubArray(ArraySupport.newArray("[1, 2, 2, 3, 1, 4, 2]")));
    Assert.assertEquals(2, solution.findShortestSubArray(ArraySupport.newArray("[3, 3, 4]")));
    Assert.assertEquals(2, solution.findShortestSubArray(ArraySupport.newArray("[49999, 0, 0]")));
  }

}
