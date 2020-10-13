package xyz.flysium.photon.algorithm.array.traverse.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0414_ThirdMaximumNumberTest {

  @Test
  public void test() {
    T0414_ThirdMaximumNumber.Solution solution = new T0414_ThirdMaximumNumber.Solution();
    Assert.assertEquals(4, solution.thirdMax(ArraySupport.newArray("[1, 2, 3, 4, 5, 6]")));
    Assert.assertEquals(4, solution.thirdMax(ArraySupport.newArray("[5, 2, 4, 1, 3, 6, 0]")));
    Assert.assertEquals(1, solution.thirdMax(ArraySupport.newArray("[3, 2, 1]")));
    Assert.assertEquals(1, solution.thirdMax(ArraySupport.newArray("[1, 1, 1]")));
    Assert.assertEquals(2, solution.thirdMax(ArraySupport.newArray("[1, 2]")));
    Assert.assertEquals(1, solution.thirdMax(ArraySupport.newArray("[2, 2, 3, 1]")));
    Assert
      .assertEquals(-2147483648, solution.thirdMax(ArraySupport.newArray("[1, -2147483648, 2]")));
    Assert.assertEquals(7,
      solution.thirdMax(ArraySupport.newArray("[5, 2, 4, 1, 3, 6, 0, 8, 9, 7, 0]")));
  }

  @Test
  public void test1() {
    T0414_ThirdMaximumNumber_1.Solution solution = new T0414_ThirdMaximumNumber_1.Solution();
    Assert.assertEquals(4, solution.thirdMax(ArraySupport.newArray("[1, 2, 3, 4, 5, 6]")));
    Assert.assertEquals(4, solution.thirdMax(ArraySupport.newArray("[5, 2, 4, 1, 3, 6, 0]")));
    Assert.assertEquals(1, solution.thirdMax(ArraySupport.newArray("[3, 2, 1]")));
    Assert.assertEquals(1, solution.thirdMax(ArraySupport.newArray("[1, 1, 1]")));
    Assert.assertEquals(2, solution.thirdMax(ArraySupport.newArray("[1, 2]")));
    Assert.assertEquals(1, solution.thirdMax(ArraySupport.newArray("[2, 2, 3, 1]")));
    Assert
      .assertEquals(-2147483648, solution.thirdMax(ArraySupport.newArray("[1, -2147483648, 2]")));
    Assert.assertEquals(7,
      solution.thirdMax(ArraySupport.newArray("[5, 2, 4, 1, 3, 6, 0, 8, 9, 7, 0]")));
  }

  @Test
  public void test2() {
    T0414_ThirdMaximumNumber_2.Solution solution = new T0414_ThirdMaximumNumber_2.Solution();
    Assert.assertEquals(4, solution.thirdMax(ArraySupport.newArray("[1, 2, 3, 4, 5, 6]")));
    Assert.assertEquals(4, solution.thirdMax(ArraySupport.newArray("[5, 2, 4, 1, 3, 6, 0]")));
    Assert.assertEquals(1, solution.thirdMax(ArraySupport.newArray("[3, 2, 1]")));
    Assert.assertEquals(1, solution.thirdMax(ArraySupport.newArray("[1, 1, 1]")));
    Assert.assertEquals(2, solution.thirdMax(ArraySupport.newArray("[1, 2]")));
    Assert.assertEquals(1, solution.thirdMax(ArraySupport.newArray("[2, 2, 3, 1]")));
    Assert
      .assertEquals(-2147483648, solution.thirdMax(ArraySupport.newArray("[1, -2147483648, 2]")));
    Assert.assertEquals(7,
      solution.thirdMax(ArraySupport.newArray("[5, 2, 4, 1, 3, 6, 0, 8, 9, 7, 0]")));
  }
}
