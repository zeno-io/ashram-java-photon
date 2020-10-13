package xyz.flysium.photon.sort;

import xyz.flysium.photon.ArraySupport;

/**
 * 找出一个数组的所有逆序对，输出逆序对的个数
 * <pre>
 *  例子： [1,3,4,2,5,3]
 *   [3,2]
 *   [4,2]
 *   [4,3]
 *   [5,3]
 * </pre>
 *
 * @author zeno
 */
public class T04_y_ReversePair {

  public static void main(String[] args) {
    int times = 100000;
    for (int i = 0; i < times; i++) {
      int[] origin = ArraySupport.generateRandomArray(10, 100, 0, 100);
      int[] other = new int[origin.length];
      System.arraycopy(origin, 0, other, 0, origin.length);

      int expected = nativeButCorrectReversePair(origin);
      int actual = reversePair(other);
      if (actual != expected) {
        System.out.println("-> Wrong algorithm !!!");
      }
    }
    System.out.println("Finish !");
  }

  public static int reversePair(int[] arr) {
    if (arr == null || arr.length < 2) {
      return 0;
    }
    return reversePair(arr, 0, arr.length - 1);
  }

  public static int reversePair(int[] arr, int l, int r) {
    if (l == r) {
      return 0;
    }
    int m = l + ((r - l) >> 1);
    return reversePair(arr, l, m)
      + reversePair(arr, m + 1, r)
      + merge(arr, l, m, r);
  }

  private static int merge(int[] arr, int l, int m, int r) {
    int x = l;
    int y = m + 1;
    int[] helper = new int[r - l + 1];
    int p = 0;
    int res = 0;
    // 数组整体采用从大到小排序，找出右边比它小的数
    while (x <= m && y <= r) {
      // 左组的数严格大于右组的数，计算个数，拷贝左组
      // 左组的数等于右组的数，拷贝右组
      // 左组的数小于右组的数，拷贝右组
      if (arr[x] > arr[y]) {
        res += (r - y + 1);
        helper[p++] = arr[x++];
      } else {
        helper[p++] = arr[y++];
      }
    }
    while (x <= m) {
      helper[p++] = arr[x++];
    }
    while (y <= r) {
      helper[p++] = arr[y++];
    }
    if (helper.length >= 0) {
      System.arraycopy(helper, 0, arr, l, helper.length);
    }
    return res;
  }

  public static int nativeButCorrectReversePair(int[] arr) {
    int res = 0;
    for (int i = 0; i < arr.length; i++) {
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[j] < arr[i]) {
          res++;
        }
      }
    }
    return res;
  }
}
