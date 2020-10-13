package xyz.flysium.photon.algorithm.array.statistics.hard;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0041_FirstMissingPositiveTest {

  @Test
  public void test() {
    T0041_FirstMissingPositive.Solution solution = new T0041_FirstMissingPositive.Solution();
    Assert.assertEquals(1, solution.firstMissingPositive(ArraySupport.newArray("[]")));
    Assert.assertEquals(2, solution.firstMissingPositive(ArraySupport.newArray("[1]")));
    Assert.assertEquals(2, solution.firstMissingPositive(ArraySupport.newArray("[1, 1]")));
    Assert.assertEquals(5, solution.firstMissingPositive(ArraySupport.newArray("[1, 2, 3, 4]")));
    Assert.assertEquals(5, solution.firstMissingPositive(ArraySupport.newArray("[4, 3, 1, 2]")));
    Assert.assertEquals(3, solution.firstMissingPositive(ArraySupport.newArray("[0, 1, 2]")));
    Assert.assertEquals(3, solution.firstMissingPositive(ArraySupport.newArray("[1, 2, 0]")));
    Assert
      .assertEquals(1, solution.firstMissingPositive(ArraySupport.newArray("[7, 8, 9, 11, 12]")));
    Assert.assertEquals(2, solution.firstMissingPositive(ArraySupport.newArray("[1, 1000]")));
    Assert.assertEquals(2, solution.firstMissingPositive(ArraySupport.newArray("[1000, 1]")));
    Assert.assertEquals(1, solution.firstMissingPositive(ArraySupport.newArray("[-1000]")));
    Assert.assertEquals(1, solution.firstMissingPositive(ArraySupport.newArray("[-1]")));
    Assert.assertEquals(2, solution.firstMissingPositive(ArraySupport.newArray("[-1, 1]")));
    Assert.assertEquals(2, solution.firstMissingPositive(ArraySupport.newArray("[-1, 0, 1]")));
    Assert.assertEquals(1, solution.firstMissingPositive(ArraySupport.newArray("[-1, 0, 1000]")));
    Assert.assertEquals(1, solution.firstMissingPositive(ArraySupport.newArray("[-1, 0, -1000]")));
    Assert.assertEquals(1, solution.firstMissingPositive(ArraySupport.newArray("[-1, 0, -1000]")));
    Assert.assertEquals(2, solution.firstMissingPositive(ArraySupport.newArray("[3, 4, -1, 1]")));
  }
}
