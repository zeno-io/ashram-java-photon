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
 * 异或运算：怎么计算一个 int 中的 bit为1的个数
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T04_Bit2 {

    public static void main(String[] args) {
        T04_Bit2 that = new T04_Bit2();

        System.out.println(that.getCountOfBitOne(10));
        System.out.println(that.getCountOfBitOne(8));
        System.out.println(that.getCountOfBitOne(7));
    }

    public int getCountOfBitOne(int m) {
        int count = 0;
        while (m != 0) {
            int rightOne = m & ((~m) + 1);
            m = m ^ rightOne;
            // m -= rightOne 对于负数是不对的
            count++;
        }
        return count;
    }

}
