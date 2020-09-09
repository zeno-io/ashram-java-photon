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
 * 如何用栈结构实现队列结构
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T06_StackUsingTwoQueues {

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int times = 100000;
        int size = 100;
        int maxValue = 100;

        for (int i = 0; i < times; i++) {
            Stack<Integer> jdkStack = new Stack<>();
            MyStackUsingQueue<Integer> myStack = new MyStackUsingQueue<>();

            for (int j = 0; j < size; j++) {
                if (jdkStack.isEmpty()) {
                    int value = random.nextInt(maxValue);
                    jdkStack.push(value);
                    myStack.push(value);
                }
                else {
                    int f = random.nextInt(100);
                    if (f < 25) {
                        Integer v1 = jdkStack.pop();
                        Integer v2 = myStack.pop();
                        if (!Objects.equals(v1, v2)) {
                            System.out.println("-> Wrong algorithm !!!");
                        }
                    }
                    else if (f < 50) {
                        Integer v1 = jdkStack.peek();
                        Integer v2 = myStack.peek();
                        if (!Objects.equals(v1, v2)) {
                            System.out.println("-> Wrong algorithm !!!");
                        }
                    }
                    else {
                        int value = random.nextInt(maxValue);
                        jdkStack.push(value);
                        myStack.push(value);
                    }
                }
            }
        }
        System.out.println("Finish !");
    }

    static class MyStackUsingQueue<E> {

        private Queue<E> data = new LinkedList<>();

        private Queue<E> help = new LinkedList<>();

        public void push(E value) {
            data.offer(value);
        }

        public E pop() {
            if (isEmpty()) {
                throw new IllegalStateException("empty !");
            }
            while (data.size() > 1) {
                help.offer(data.poll());
            }
            E e = data.poll();
            //      while (help.size() > 0) {
            //        data.offer(help.poll());
            //      }
            Queue<E> tmp = data;
            data = help;
            help = tmp;
            return e;
        }

        public E peek() {
            if (isEmpty()) {
                throw new IllegalStateException("empty !");
            }
            while (data.size() > 1) {
                help.offer(data.poll());
            }
            E e = data.poll();
            Queue<E> tmp = data;
            data = help;
            help = tmp;
            data.offer(e);
            return e;
        }

        public boolean isEmpty() {
            return data.isEmpty();
        }

    }

}
