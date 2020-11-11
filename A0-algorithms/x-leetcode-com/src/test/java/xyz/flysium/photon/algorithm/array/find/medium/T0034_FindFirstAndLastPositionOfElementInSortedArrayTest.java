package xyz.flysium.photon.algorithm.array.find.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0034_FindFirstAndLastPositionOfElementInSortedArrayTest {

  @Test
  public void test() {
    T0034_FindFirstAndLastPositionOfElementInSortedArray.Solution solution = new T0034_FindFirstAndLastPositionOfElementInSortedArray.Solution();
    int[] arr = new int[]{1, 2, 3, 3, 4, 5, 6, 6, 7, 8, 9};
    Assert.assertArrayEquals(ArraySupport.newArray("[2, 3]"), solution.searchRange(arr, 3));
    Assert.assertArrayEquals(ArraySupport.newArray("[6, 7]"), solution.searchRange(arr, 6));
    Assert.assertArrayEquals(ArraySupport.newArray("[-1, -1]"), solution.searchRange(arr, 10));
    Assert.assertArrayEquals(ArraySupport.newArray("[-1, -1]"), solution.searchRange(arr, 0));
  }

}
