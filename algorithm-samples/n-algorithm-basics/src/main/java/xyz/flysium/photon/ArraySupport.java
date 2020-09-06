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

package xyz.flysium.photon;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public final class ArraySupport {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private ArraySupport() {
    }

    public static void print(int[] array, Consumer<Integer> printConsumer, Consumer<Void> lastPrint) {
        for (int j : array) {
            if (printConsumer != null) {
                printConsumer.accept(j);
            }
        }
        if (lastPrint != null) {
            lastPrint.accept(null);
        }
    }

    public static boolean equals(int[] src, int[] dest) {
        if (src == null) {
            return dest == null;
        }
        if (dest == null) {
            return false;
        }
        if (src.length != dest.length) {
            return false;
        }
        int length = dest.length;
        for (int i = 0; i < length; i++) {
            if (src[i] != dest[i]) {
                return false;
            }
        }
        return true;
    }

    private static int randomValue(int minValue, int maxValue) {
        return (minValue == maxValue) ? minValue : RANDOM.nextInt(minValue, maxValue);
    }

    public static int[] generateRandomArray() {
        return generateRandomArray(10, 100, 0, 1000);
    }

    public static int[] generateRandomArray(int minSize, int maxSize, int minValue, int maxValue) {
        minSize = Math.max(minSize, 2);
        maxSize = Math.max(minSize, maxSize);
        maxValue = Math.max(minValue, maxValue);

        int size = randomValue(minSize, maxSize);
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = randomValue(minValue, maxValue);
        }
        return array;
    }

}
