package xyz.flysium.photon.sort;

import xyz.flysium.photon.SortSupport;

/**
 * 计数排序, 桶排序的一种
 * <p>
 * 不基于比较的排序，使用场景需要知道样本的分布情况
 *
 * @author zeno
 */
public class T07_CountingSort {

  public static void main(String[] args) {
    SortSupport.testToEnd(100000, 10, 100, 10, 1000, new T07_CountingSort()::sort);
    System.out.println("Finish !");
  }

  public void sort(int[] arr) {
    int minValue = Integer.MAX_VALUE;
    int maxValue = Integer.MIN_VALUE;

    for (int e : arr) {
      if (e < minValue) {
        minValue = e;
      }
      if (e > maxValue) {
        maxValue = e;
      }
    }
    if (maxValue - minValue >= 10000) {
      throw new IllegalStateException("too big for counting sort !");
    }

    sort(arr, minValue, maxValue);
  }

  public void sort(int[] arr, int minValue, int maxValue) {
    int[] helper = new int[maxValue - minValue + 1];
    for (int e : arr) {
      helper[e - minValue]++;
    }
    int index = 0;
    for (int i = 0; i < helper.length; i++) {
      if (helper[i] > 0) {
        for (int j = 0; j < helper[i]; j++) {
          arr[index++] = minValue + i;
        }
      }
    }
  }

}
