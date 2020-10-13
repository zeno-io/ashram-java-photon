package xyz.flysium.photon.algorithm.array.rotate.medium;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0396_RotateFunctionTest {

  @Test
  public void test() {
    T0396_RotateFunction.Solution solution = new T0396_RotateFunction.Solution();
    int actual = 0, expected = 0;
    int[] nums = null;

    nums = ArraySupport.newArray("[4, 3, 2, 6]");
    actual = solution.maxRotateFunction(nums);
    expected = maxRotateFunction(nums);
    System.out.println("expected=" + expected);
    Assert.assertEquals(expected, actual);

    nums = ArraySupport.newArray(
      "[4, 3, 2, 6, 3, 1, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3 ]");
    actual = solution.maxRotateFunction(nums);
    expected = maxRotateFunction(nums);
    System.out.println("expected=" + expected);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test1() {
    T0396_RotateFunction_1.Solution solution = new T0396_RotateFunction_1.Solution();
    int actual = 0, expected = 0;
    int[] nums = null;

    nums = ArraySupport.newArray("[4, 3, 2, 6]");
    actual = solution.maxRotateFunction(nums);
    expected = maxRotateFunction(nums);
    System.out.println("expected=" + expected);
    Assert.assertEquals(expected, actual);

    nums = ArraySupport.newArray(
      "[4, 3, 2, 6, 3, 1, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3, 1, 3, 4, 5, 6, 7, 0, 100, 102, 5, 3 ]");
    actual = solution.maxRotateFunction(nums);
    expected = maxRotateFunction(nums);
    System.out.println("expected=" + expected);
    Assert.assertEquals(expected, actual);
  }

  public int maxRotateFunction(int[] A) {
    final int n = A.length;

    int maxFn = f(A);
    for (int k = 1; k <= n - 1; k++) {
      rotate(A, 1);
      maxFn = Math.max(maxFn, f(A));
    }
    return maxFn;
  }

  private int f(int[] A) {
    int fn = 0;
    for (int i = 0; i < A.length; i++) {
      fn += i * A[i];
    }
    return fn;
  }

  private void rotate(int[] nums, int k) {
    final int t = k % nums.length;
    if (t == 0) {
      return;
    }
    // reverse
    reverse(nums, 0, nums.length - 1);
    reverse(nums, 0, t - 1);
    reverse(nums, t, nums.length - 1);
  }

  private void reverse(int[] arr, int l, int r) {
    int length = r - l + 1;
    for (int i = 0; i < (length >> 1); i++) {
      swap(arr, l + i, r - i);
    }
  }

  private void swap(int[] arr, int x, int y) {
    if (x == y) {
      return;
    }
    arr[x] = arr[x] ^ arr[y];
    arr[y] = arr[x] ^ arr[y];
    arr[x] = arr[x] ^ arr[y];
  }

}
