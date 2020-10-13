package xyz.flysium.photon.algorithm.array.traverse.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0628_MaximumProductOfThreeNumbersTest {

  @Test
  public void test() {
    T0628_MaximumProductOfThreeNumbers.Solution solution = new T0628_MaximumProductOfThreeNumbers.Solution();
    Assert.assertEquals(9, solution.maximumProduct(ArraySupport.newArray("[-1, -3, 1, 2, 3]")));
    Assert.assertEquals(36, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3, 3, 4]")));
    Assert.assertEquals(6, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3]")));
    Assert.assertEquals(24, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3, 4]")));
    Assert.assertEquals(1, solution.maximumProduct(ArraySupport.newArray("[1, 1, 1]")));
    Assert.assertEquals(0, solution.maximumProduct(ArraySupport.newArray("[1, 1, 0]")));
    Assert.assertEquals(24, solution.maximumProduct(ArraySupport.newArray("[-1, -2, 2, 2, 6]")));
    Assert.assertEquals(0, solution.maximumProduct(ArraySupport.newArray("[4, 0, 0, 4]")));
  }

  @Test
  public void test1() {
    T0628_MaximumProductOfThreeNumbers_1.Solution solution = new T0628_MaximumProductOfThreeNumbers_1.Solution();
    Assert.assertEquals(9, solution.maximumProduct(ArraySupport.newArray("[-1, -3, 1, 2, 3]")));
    Assert.assertEquals(36, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3, 3, 4]")));
    Assert.assertEquals(6, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3]")));
    Assert.assertEquals(24, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3, 4]")));
    Assert.assertEquals(1, solution.maximumProduct(ArraySupport.newArray("[1, 1, 1]")));
    Assert.assertEquals(0, solution.maximumProduct(ArraySupport.newArray("[1, 1, 0]")));
    Assert.assertEquals(24, solution.maximumProduct(ArraySupport.newArray("[-1, -2, 2, 2, 6]")));
    Assert.assertEquals(0, solution.maximumProduct(ArraySupport.newArray("[4, 0, 0, 4]")));
  }

  @Test
  public void test2() {
    T0628_MaximumProductOfThreeNumbers_2.Solution solution = new T0628_MaximumProductOfThreeNumbers_2.Solution();
    Assert.assertEquals(9, solution.maximumProduct(ArraySupport.newArray("[-1, -3, 1, 2, 3]")));
    Assert.assertEquals(36, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3, 3, 4]")));
    Assert.assertEquals(6, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3]")));
    Assert.assertEquals(24, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3, 4]")));
    Assert.assertEquals(1, solution.maximumProduct(ArraySupport.newArray("[1, 1, 1]")));
    Assert.assertEquals(0, solution.maximumProduct(ArraySupport.newArray("[1, 1, 0]")));
    Assert.assertEquals(24, solution.maximumProduct(ArraySupport.newArray("[-1, -2, 2, 2, 6]")));
    Assert.assertEquals(0, solution.maximumProduct(ArraySupport.newArray("[4, 0, 0, 4]")));
  }

  @Test
  public void test3() {
    T0628_MaximumProductOfThreeNumbers_3.Solution solution = new T0628_MaximumProductOfThreeNumbers_3.Solution();
    Assert.assertEquals(9, solution.maximumProduct(ArraySupport.newArray("[-1, -3, 1, 2, 3]")));
    Assert.assertEquals(36, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3, 3, 4]")));
    Assert.assertEquals(6, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3]")));
    Assert.assertEquals(24, solution.maximumProduct(ArraySupport.newArray("[1, 2, 3, 4]")));
    Assert.assertEquals(1, solution.maximumProduct(ArraySupport.newArray("[1, 1, 1]")));
    Assert.assertEquals(0, solution.maximumProduct(ArraySupport.newArray("[1, 1, 0]")));
    Assert.assertEquals(24, solution.maximumProduct(ArraySupport.newArray("[-1, -2, 2, 2, 6]")));
    Assert.assertEquals(0, solution.maximumProduct(ArraySupport.newArray("[4, 0, 0, 4]")));
  }
}
