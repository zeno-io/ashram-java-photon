package xyz.flysium.photon.sort;

import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.CommonSort;

/**
 * 给定一个数组arr，和一个整数num。请把小于等于num的数放在数组的左边，大于num的数放在数组的右边。
 * <p>
 * 要求额外空间复杂度O(1)，时间复杂度O(N)
 *
 * @author zeno
 */
public class T05_Partition {

  public static void main(String[] args) {
    int times = 100000;
    for (int i = 0; i < times; i++) {
      int[] origin = ArraySupport.generateRandomArray(10, 100, 0, 100);
      int[] other1 = new int[origin.length];
      int[] other2 = new int[origin.length];
      System.arraycopy(origin, 0, other1, 0, origin.length);
      System.arraycopy(origin, 0, other2, 0, origin.length);
      int num = origin[origin.length - 1];

      int expected = nativeButCorrectPartition(other1, num);
      int actual = partition(other2, num);

      if (expected != actual
        || !ArraySupport.equals(other1, 0, other2, 0, expected)
      ) {
        System.out.println("-> Wrong algorithm !!!");
      }
    }
    System.out.println("Finish !");
  }


  public static int partition(int[] arr, int num) {
    final int length = arr.length;
    int ltq = -1;

    int index = 0;
    while (index < length) {
      if (arr[index] <= num) {
        CommonSort.swap(arr, index++, ++ltq);
      } else {
        index++;
      }
    }
    return ltq;
  }

  public static int nativeButCorrectPartition(int[] arr, int num) {
    int[] ltq = new int[arr.length];
    int[] gt = new int[arr.length];
    int x = -1;
    int y = -1;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] <= num) {
        ltq[++x] = arr[i];
      } else {
        gt[++y] = arr[i];
      }
    }
    if (x >= 0) {
      System.arraycopy(ltq, 0, arr, 0, x + 1);
    }
    if (y >= 0) {
      System.arraycopy(gt, 0, arr, x + 1, y + 1);
    }

    return x;
  }
}
