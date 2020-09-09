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

import java.util.ArrayList;
import java.util.Collections;
import xyz.flysium.photon.ArraySupport;

/**
 * 异或运算: 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T03_EvenTimesOddTimes {

    public static void main(String[] args) {
        T03_EvenTimesOddTimes that = new T03_EvenTimesOddTimes();
        int times = 100000;
        boolean succeed = true;
        for (int i = 0; i < times; i++) {
            int[] arr = ArraySupport.generateRandomArray();
            int[] spec = new int[arr.length * 2 + 1];
            System.arraycopy(arr, 0, spec, 0, arr.length);
            System.arraycopy(arr, 0, spec, arr.length, arr.length);
            spec[spec.length - 1] = arr[0];
            ArrayList<Integer> list = new ArrayList<>(spec.length);
            for (int k : spec) {
                list.add(k);
            }
            Collections.shuffle(list);
            int[] rs = new int[list.size()];
            for (int j = 0; j < rs.length; j++) {
                rs[j] = list.get(j);
            }

            int eor = that.getOddTimesNum(rs);
            int p = 0;
            for (int r : rs) {
                if (r == eor) {
                    p++;
                }
            }
            if (p % 2 == 0) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Correct ~" : "Wrong !!!");

        int[] a = { 1, 4, 3, 2, 3, 2, 1, 4, 3 };
        int eor = that.getOddTimesNum(a);
        System.out.println(eor);
    }

    public int getOddTimesNum(int[] a) {
        assert (a != null);
        if (a.length == 1) {
            return a[0];
        }
        int eor = 0;
        for (int j : a) {
            eor = eor ^ j;
        }
        return eor;
    }

}
