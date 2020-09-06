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

package xyz.flysium.photon.xor;

/**
 * 异或运算: 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T03_EvenTimesOddTimes2 {

    public static void main(String[] args) {
        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        new T03_EvenTimesOddTimes2().printOddTimesNum2(arr2);
    }

    public void printOddTimesNum2(int[] a) {
        assert (a != null);
        int eor = 0;
        for (int j : a) {
            eor = eor ^ j;
        }
        // eor = a ^ b
        // eor != 0
        // eor必然有一个位置上是1
        // 0110010000
        // 0000010000
        int rightMostBitOne = eor & (~eor + 1); // 提取出最右的1
        int onlyOne = 0;
        for (int i = 0; i < a.length; i++) {
            //  arr[1] =  111100011110000
            // rightOne=  000000000010000
            if ((rightMostBitOne & a[i]) != 0) {
                onlyOne = onlyOne ^ a[i];
            }
        }

        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }

}
