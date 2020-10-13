package xyz.flysium.photon.algorithm.array.statistics.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0274_HIndexTest {

  @Test
  public void test() {
    T0274_HIndex.Solution solution = new T0274_HIndex.Solution();
    Assert.assertEquals(0, solution.hIndex(ArraySupport.newArray("[]")));
    Assert.assertEquals(0, solution.hIndex(ArraySupport.newArray("[0]")));
    Assert.assertEquals(0, solution.hIndex(ArraySupport.newArray("[0, 0, 0, 0]")));
    Assert.assertEquals(1, solution.hIndex(ArraySupport.newArray("[100]")));
    Assert.assertEquals(1, solution.hIndex(ArraySupport.newArray("[1, 2]")));
    Assert.assertEquals(2, solution.hIndex(ArraySupport.newArray("[11, 15]")));
    Assert.assertEquals(1, solution.hIndex(ArraySupport.newArray("[1, 0, 1]")));
    Assert.assertEquals(2, solution.hIndex(ArraySupport.newArray("[1, 2, 1, 2]")));
    Assert.assertEquals(3, solution.hIndex(ArraySupport.newArray("[3, 0, 6, 1, 5]")));
  }

  @Test
  public void test1() {
    T0274_HIndex_1.Solution solution = new T0274_HIndex_1.Solution();
    Assert.assertEquals(0, solution.hIndex(ArraySupport.newArray("[]")));
    Assert.assertEquals(0, solution.hIndex(ArraySupport.newArray("[0]")));
    Assert.assertEquals(0, solution.hIndex(ArraySupport.newArray("[0, 0, 0, 0]")));
    Assert.assertEquals(1, solution.hIndex(ArraySupport.newArray("[100]")));
    Assert.assertEquals(1, solution.hIndex(ArraySupport.newArray("[1, 2]")));
    Assert.assertEquals(1, solution.hIndex(ArraySupport.newArray("[1, 0, 1]")));
    Assert.assertEquals(2, solution.hIndex(ArraySupport.newArray("[11, 15]")));
    Assert.assertEquals(2, solution.hIndex(ArraySupport.newArray("[1, 2, 1, 2]")));
    Assert.assertEquals(3, solution.hIndex(ArraySupport.newArray("[3, 0, 6, 1, 5]")));
  }
}
