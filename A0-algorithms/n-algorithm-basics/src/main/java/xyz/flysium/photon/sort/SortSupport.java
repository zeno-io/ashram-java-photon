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

import java.util.Arrays;
import java.util.function.Consumer;
import xyz.flysium.photon.ArraySupport;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public final class SortSupport {

    private SortSupport() {
    }

    public static void testToEnd(int times, int minSize, int maxSize, int minValue, int maxValue,
        Consumer<int[]> sortFunction) {
        testToEnd(times, minSize, maxSize, minValue, maxValue, sortFunction, (x) -> System.out.print(x + " "),
            (x) -> System.out.println());
    }

    public static void testToEnd(int times, int minSize, int maxSize, int minValue, int maxValue,
        Consumer<int[]> sortFunction, Consumer<Integer> printConsumer, Consumer<Void> lastPrint) {
        boolean succeed = backToBackTest(times, minSize, maxSize, minValue, maxValue, sortFunction, printConsumer,
            lastPrint);

        if (succeed) {
            System.out.println();
            System.out.println("-> Correct algorithm ~");
            System.out.println();

            test(minSize, maxSize, minValue, maxValue, sortFunction, printConsumer, lastPrint);
        }
        else {
            System.err.println();
            System.err.println("-> Wrong algorithm !!!");
            System.err.println();
        }
    }

    public static boolean backToBackTest(int times, int minSize, int maxSize, int minValue, int maxValue,
        Consumer<int[]> sortFunction, Consumer<Integer> printConsumer, Consumer<Void> lastPrint) {
        if (sortFunction == null) {
            return true;
        }
        minSize = Math.max(minSize, 2);
        maxSize = Math.max(minSize, maxSize);
        maxValue = Math.max(minValue, maxValue);

        for (int i = 0; i < times; i++) {
            int[] array = ArraySupport.generateRandomArray(minSize, maxSize, minValue, maxValue);
            if (!compare(array, sortFunction, printConsumer, lastPrint)) {
                return false;
            }
        }

        return true;
    }

    public static void test(int minSize, int maxSize, int minValue, int maxValue, Consumer<int[]> sortFunction,
        Consumer<Integer> printConsumer, Consumer<Void> lastPrint) {
        if (sortFunction == null) {
            return;
        }
        minSize = Math.max(minSize, 2);
        maxSize = Math.max(minSize, maxSize);
        maxValue = Math.max(minValue, maxValue);

        int[] array = ArraySupport.generateRandomArray(minSize, maxSize, minValue, maxValue);
        ArraySupport.print(array, printConsumer, lastPrint);
        sortFunction.accept(array);
        ArraySupport.print(array, printConsumer, lastPrint);
    }

    protected static boolean compare(int[] origin, Consumer<int[]> sortFunction, Consumer<Integer> printConsumer,
        Consumer<Void> lastPrint) {
        int[] other1 = new int[origin.length];
        int[] other2 = new int[origin.length];
        System.arraycopy(origin, 0, other1, 0, origin.length);
        System.arraycopy(origin, 0, other2, 0, origin.length);

        arraysSort(other1);
        sortFunction.accept(other2);

        boolean equals = ArraySupport.equals(other1, other2);
        if (!equals) {
            ArraySupport.print(origin, printConsumer, lastPrint);
            ArraySupport.print(other1, printConsumer, lastPrint);
            ArraySupport.print(other2, printConsumer, lastPrint);
        }
        return equals;
    }

    protected static void arraysSort(int[] array) {
        Arrays.sort(array);
    }

}
