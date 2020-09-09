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

package xyz.flysium.photon.list;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 如何用队列结构实现栈结构
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T07_QueueUsingTwoStacks {

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int times = 100000;
        int size = 100;
        int maxValue = 100;

        for (int i = 0; i < times; i++) {
            Queue<Integer> jdkQueue = new LinkedList<>();
            MyQueueUsingStacks<Integer> myQueue = new MyQueueUsingStacks<>();

            for (int j = 0; j < size; j++) {
                if (jdkQueue.isEmpty()) {
                    int value = random.nextInt(maxValue);
                    jdkQueue.offer(value);
                    myQueue.offer(value);
                }
                else {
                    int f = random.nextInt(100);
                    if (f < 25) {
                        Integer v1 = jdkQueue.poll();
                        Integer v2 = myQueue.poll();
                        if (!Objects.equals(v1, v2)) {
                            System.out.println("-> Wrong algorithm !!!");
                        }
                    }
                    else {
                        int value = random.nextInt(maxValue);
                        jdkQueue.offer(value);
                        myQueue.offer(value);
                    }
                }
            }
        }
        System.out.println("Finish !");
    }

    static class MyQueueUsingStacks<E> {

        private final Stack<E> stackPush = new Stack<>();

        private final Stack<E> stackPop = new Stack<>();

        public void offer(E value) {
            stackPush.push(value);
            pushToPop();
        }

        private void pushToPop() {
            if (stackPop.isEmpty()) {
                while (stackPush.size() > 0) {
                    stackPop.push(stackPush.pop());
                }
            }
        }

        public E poll() {
            if (isEmpty()) {
                throw new IllegalStateException("empty !");
            }
            pushToPop();
            return stackPop.pop();
        }

        public boolean isEmpty() {
            return stackPush.isEmpty() && stackPop.isEmpty();
        }

    }

}
