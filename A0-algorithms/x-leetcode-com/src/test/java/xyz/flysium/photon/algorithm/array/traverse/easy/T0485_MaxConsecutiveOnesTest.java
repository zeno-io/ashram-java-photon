package xyz.flysium.photon.algorithm.array.traverse.easy;


import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0485_MaxConsecutiveOnesTest {

  @Test
  public void test() {
    T0485_MaxConsecutiveOnes.Solution solution = new T0485_MaxConsecutiveOnes.Solution();
    Assert.assertEquals(4,
      solution.findMaxConsecutiveOnes(
        ArraySupport.newArray("[1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0]")));
    Assert.assertEquals(3, solution
      .findMaxConsecutiveOnes(ArraySupport.newArray("[0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0]")));
    Assert
      .assertEquals(3,
        solution.findMaxConsecutiveOnes(ArraySupport.newArray("[1, 1, 0, 1, 1, 1]")));
    Assert
      .assertEquals(5,
        solution.findMaxConsecutiveOnes(ArraySupport.newArray("[0, 1, 1, 1, 1, 1]")));
    Assert
      .assertEquals(6,
        solution.findMaxConsecutiveOnes(ArraySupport.newArray("[1, 1, 1, 1, 1, 1]")));
    Assert
      .assertEquals(1,
        solution.findMaxConsecutiveOnes(ArraySupport.newArray("[0, 0, 0, 0, 0, 1]")));
    Assert
      .assertEquals(1,
        solution.findMaxConsecutiveOnes(ArraySupport.newArray("[1, 0, 0, 0, 0, 0]")));
    Assert
      .assertEquals(1,
        solution.findMaxConsecutiveOnes(ArraySupport.newArray("[0, 0, 0, 1, 0, 0]")));

    int[] arr = new int[10000];
    arr[454] = 1;
    arr[455] = 1;
    arr[787] = 1;
    arr[788] = 1;
    arr[790] = 1;
    arr[792] = 1;
    arr[793] = 1;
    Assert.assertEquals(2, solution.findMaxConsecutiveOnes(arr));
  }

}
