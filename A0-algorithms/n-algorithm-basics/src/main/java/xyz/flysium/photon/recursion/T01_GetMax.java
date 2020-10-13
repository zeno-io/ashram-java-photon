/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.recursion;

import java.util.Arrays;
import xyz.flysium.photon.ArraySupport;

/**
 * 求数组arr[L..R]中的最大值，怎么用递归方法实现。
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T01_GetMax {

  public static void main(String[] args) {
    int times = 100000;
    for (int i = 0; i < times; i++) {
      int[] ints = ArraySupport.generateRandomArray(10, 100, 10, 100);
      int max = getMax(ints);
      if (jdkMax(ints) != max) {
        System.out.println("-> Wrong algorithm !!!");
      }
    }
    System.out.println("Finish !");
  }

  /**
   * 求数组arr[L..R]中的最大值，怎么用递归方法实现。
   *
   * @param arr 数组
   * @return 数组中的最大值
   */
  public static int getMax(int[] arr) {
    return getMax(arr, 0, arr.length - 1);
  }

  public static int getMax(int[] arr, int l, int r) {
    if (l == r) {
      return arr[l];
    }
    int mid = l + ((r - l) >> 1);
    int maxInLeft = getMax(arr, l, mid);
    int maxInRight = getMax(arr, mid + 1, r);
    return Math.max(maxInLeft, maxInRight);
  }

  public static int jdkMax(int[] arr) {
    return Arrays.stream(arr).max().getAsInt();
  }

}
