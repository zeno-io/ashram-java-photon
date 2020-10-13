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

package xyz.flysium.photon.sort;

import xyz.flysium.photon.SortSupport;

/**
 * 归并排序（Merge Sort），递归方式
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T04_MergeSortWithinRecusion {

  public static void main(String[] args) {
        SortSupport.testToEnd(100000, 10, 100, 0, 1000, new T04_MergeSortWithinRecusion()::sort);
        System.out.println("Finish !");
    }

    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    protected void sort(int[] array, int left, int right) {
        if (left == right) {
            return;
        }
        int middle = left + ((right - left) >> 1);
        sort(array, left, middle);
        sort(array, middle + 1, right);
        merge(array, left, right, middle);
    }

    protected void merge(int[] array, final int left, final int right, final int middle) {
        if (left > middle || middle > right || right >= array.length) {
            System.err.println("wrong -> length=" + array.length + ", " + left + ", " + right + ", " + middle);
            return;
        }
        final int len = right - left + 1;
        if (len <= 1) {
            return;
        }
        int[] helper = new int[len];

        int i = 0;
        int x = left;
        int y = middle + 1;

        while (x <= middle && y <= right) {
            if (array[x] <= array[y]) {
                helper[i++] = array[x++];
            }
            else {
                helper[i++] = array[y++];
            }
        }
        // 要么 x 越界，或者 y 越界
        while (y <= right) {
            helper[i++] = array[y++];
        }
        while (x <= middle) {
            helper[i++] = array[x++];
        }
        System.arraycopy(helper, 0, array, left, len);
    }

}
