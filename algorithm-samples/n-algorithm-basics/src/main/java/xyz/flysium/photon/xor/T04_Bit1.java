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
 * 异或运算：怎么把一个int类型的数，提取出最右侧的1来
 * <p>
 * N = 000010010100010000000 (bit)
 * <p>
 * ANS = 000000000000010000000 (bit)
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T04_Bit1 {

    /**
     * <pre>
     * N = 000010010100010000000 (bit)
     * ~N = 111101101011101111111 (bit)
     * ~N+1 = 111101101011110000000 (bit)
     *  ANS = 000000000000010000000 (bit)
     * </pre>
     */
    public static void main(String[] args) {
        T04_Bit1 that = new T04_Bit1();

        System.out.println(that.getRightMostBitOne(10));
        System.out.println(that.getRightMostBitOne(8));
        System.out.println(that.getRightMostBitOne(7));
    }

    public int getRightMostBitOne(int m) {
        return m & ((~m) + 1);
    }

}
