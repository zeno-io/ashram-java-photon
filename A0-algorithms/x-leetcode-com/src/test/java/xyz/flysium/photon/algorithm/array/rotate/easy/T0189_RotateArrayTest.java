package xyz.flysium.photon.algorithm.array.rotate.easy;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0189_RotateArrayTest {

  @Test
  public void test() {
    T0189_RotateArray.Solution solution = new T0189_RotateArray.Solution();
    int[] nums = null;

    nums = ArraySupport.newArray("[1,2,3,4,5,6,7]");
    solution.rotate(nums, 3);
    Assert.assertArrayEquals(ArraySupport.newArray("[5,6,7,1,2,3,4]"), nums);
  }
}
