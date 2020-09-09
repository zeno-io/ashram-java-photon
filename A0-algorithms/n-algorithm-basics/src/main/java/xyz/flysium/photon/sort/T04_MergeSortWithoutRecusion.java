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

/**
 * 归并排序（Merge Sort）, 非递归方式
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T04_MergeSortWithoutRecusion extends T04_MergeSortWithinRecusion {

    public static void main(String[] args) {
        SortSupport.testToEnd(100000, 10, 100, 0, 1000, new T04_MergeSortWithoutRecusion()::sort);
        System.out.println("Finish !");
    }

    @Override
    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        final int length = array.length;

        // 当前有序的，左组长度
        int mergeSize = 1;
        // log N
        while (mergeSize < length) {

            int left = 0;
            while (left < length) {
                // 左组 left, middle
                int middle = left + mergeSize - 1;
                if (middle >= length) {
                    break;
                }
                // 右组 middle+1, right (middle+ mergeSize)
                int right = Math.min(middle + mergeSize, length - 1);

                merge(array, left, right, middle);

                left = right + 1;
            }
            // 为了避免 mergeSize 越界超过 int 的最大值
            if (mergeSize > length / 2) {
                break;
            }

            mergeSize <<= 1;
        }

    }

}
