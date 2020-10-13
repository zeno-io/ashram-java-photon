package xyz.flysium.photon.sort;

import xyz.flysium.photon.CommonSort;
import xyz.flysium.photon.SortSupport;

/**
 * 快速排序算法1: (时间复杂度 N^2)
 * <p>
 * 利用 partition，每次依据段内最右一个数进行划分 （<=区，>区）那么得到下标，可以确定一个数的位置
 * <p>
 * 然后继续操作  <=区
 * <p>
 * 然后继续操作  >区
 *
 * @author zeno
 */
public class T05_QuickSort_1 {

  public static void main(String[] args) {
    SortSupport.testToEnd(100000, 10, 100, 0, 1000, new T05_QuickSort_1()::sort);
    System.out.println("Finish !");
  }

  // 在arr[L..R]范围上，进行快速排序的过程：
  // 1）用arr[R]对该范围做partition，<= arr[R]的数在左部分并且保证arr[R]最后来到左部分的最后一个位置，记为M； <= arr[R]的数在右部分（arr[M+1..R]）
  // 2）对arr[L..M-1]进行快速排序(递归)
  // 3）对arr[M+1..R]进行快速排序(递归)
  // 因为每一次partition都会搞定一个数的位置且不会再变动，所以排序能完成
  public void sort(int[] arr) {
    if (arr == null || arr.length < 2) {
      return;
    }
    process(arr, 0, arr.length - 1);
  }

  private void process(int[] arr, int l, int r) {
    if (l >= r) {
      return;
    }
    int m = partition(arr, l, r);
    process(arr, l, m - 1);
    process(arr, m + 1, r);
  }

  private int partition(int[] arr, int l, int r) {
    if (l > r) {
      return -1;
    }
    if (l == r) {
      return l;
    }
    final int num = arr[r];

    int ltq = l - 1;
    int index = l;
    while (index < r) {
      if (arr[index] <= num) {
        CommonSort.swap(arr, index++, ++ltq);
      } else {
        index++;
      }
    }
    // 最右的一个数需要加入小于等于区
    CommonSort.swap(arr, r, ++ltq);

    return ltq;
  }

}
