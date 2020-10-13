package xyz.flysium.photon.sort;

import xyz.flysium.photon.SortSupport;

/**
 * 基数排序
 * <p>
 * 不基于比较的排序，一般要求元素都是非负数，且是十进制
 *
 * @author zeno
 */
public class T08_RadixSort {

  public static void main(String[] args) {
    SortSupport.testToEnd(100000, 10, 100, 0, 1000, new T08_RadixSort()::sort);
    System.out.println("Finish !");
//    System.out.println(new T08_RadixSort().getDigit(0));
//    System.out.println(new T08_RadixSort().getDigit(1));
//    System.out.println(new T08_RadixSort().getDigit(8));
//    System.out.println(new T08_RadixSort().getDigit(12));
//    System.out.println(new T08_RadixSort().getDigit(400));
//    System.out.println(new T08_RadixSort().getDigit(401));
//    System.out.println(new T08_RadixSort().getNumberOfDigit(400, 3));
//    System.out.println(new T08_RadixSort().getNumberOfDigit(401, 2));
  }

  public void sort(int[] arr) {
    int maxValue = Integer.MIN_VALUE;
    for (int e : arr) {
      if (e > maxValue) {
        maxValue = e;
      }
    }
    int maxDigit = getDigit(maxValue);
    for (int digit = 1; digit <= maxDigit; digit++) {

      int[] count = new int[10];
      for (int e : arr) {
        int numberOfDigit = getNumberOfDigit(e, digit);
        count[numberOfDigit]++;
      }
      // 利用数组，替换桶
      for (int i = 1; i < count.length; i++) {
        count[i] += count[i - 1];
      }
      int[] helper = new int[arr.length];
      // 从右到左，数，应该放置到对应桶的最后
      for (int i = arr.length - 1; i >= 0; i--) {
        int numberOfDigit = getNumberOfDigit(arr[i], digit);
        int c = --count[numberOfDigit];
        helper[c] = arr[i];
      }
      System.arraycopy(helper, 0, arr, 0, arr.length);
    }
  }

  private int getDigit(int num) {
    num = num < 0 ? -num : num;
    if (num == 0) {
      return 1;
    }
//    return (int) (Math.log10(num) + 1);
    int digit = 0;
    while (num > 0) {
      digit++;
      num = num / 10;
    }
    return digit;
  }

  private int getNumberOfDigit(int num, int digit) {
    return (int) (num / Math.pow(10, digit - 1)) % 10;
  }

}
