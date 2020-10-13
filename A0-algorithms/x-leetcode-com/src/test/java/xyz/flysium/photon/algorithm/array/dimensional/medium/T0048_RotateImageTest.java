package xyz.flysium.photon.algorithm.array.dimensional.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0048_RotateImageTest {

  @Test
  public void test() {
    T0048_RotateImage.Solution solution = new T0048_RotateImage.Solution();

    int[][] matrix = null;

    matrix = ArraySupport.newTwoDimensionalArray(
      "[[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]]");
    solution.rotate(matrix);
    System.out.println(ArraySupport.toString(matrix));
    Assert.assertArrayEquals(
      ArraySupport.newTwoDimensionalArray("[[13,9,5,1],[14,10,6,2],[15,11,7,3],[16,12,8,4]]"),
      matrix);

    matrix = ArraySupport.newTwoDimensionalArray(
      "[[2,29,20,26,16,28],[12,27,9,25,13,21],[32,33,32,2,28,14],[13,14,32,27,22,26],[33,1,20,7,21,7],[4,24,1,6,32,34]]");
    solution.rotate(matrix);
    System.out.println(ArraySupport.toString(matrix));
    Assert.assertArrayEquals(ArraySupport.newTwoDimensionalArray(
      "[[4,33,13,32,12,2],[24,1,14,33,27,29],[1,20,32,32,9,20],[6,7,27,2,25,26],[32,21,22,28,13,16],[34,7,26,14,21,28]]"),
      matrix);
  }
}
