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

package xyz.flysium.photon.queueandstack;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 采用 数组实现
 * <p>
 * 栈：数据先进后出，犹如弹匣
 * <p>
 * 队列：数据先进先出，好似排队 (ring)
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T03_StackAndQueueUsingArray {

  public static void main(String[] args) {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    int times = 100000;
    int size = 100;
    int maxValue = 100;

    for (int i = 0; i < times; i++) {
      Stack<Integer> jdkStack = new Stack<>();
      MyStack2<Integer> myStack = new MyStack2<>(size);

      for (int j = 0; j < size; j++) {
        if (jdkStack.isEmpty()) {
          int value = random.nextInt(maxValue);
          jdkStack.push(value);
          myStack.push(value);
        } else {
          int f = random.nextInt(100);
          if (f < 25) {
            Integer v1 = jdkStack.pop();
            Integer v2 = myStack.pop();
            if (!Objects.equals(v1, v2)) {
              System.out.println("-> Wrong algorithm !!!");
            }
          } else {
            int value = random.nextInt(maxValue);
            jdkStack.push(value);
            myStack.push(value);
          }
        }
      }
    }
        System.out.println("Finish !");

        for (int i = 0; i < times; i++) {
            Queue<Integer> jdkQueue = new LinkedList<>();
            MyQueue2<Integer> myQueue = new MyQueue2<>(size);

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

    public static class MyStack2<T> {

        private final int capacity;

        private final Object[] array;

        private int index = -1;

        public MyStack2(int capacity) {
            this.capacity = capacity;
            this.array = new Object[capacity];
        }

        public void push(T value) {
            if (index >= capacity - 1) {
                throw new IllegalStateException("over limit !");
            }
            index++;
            array[index] = value;
        }

        public T pop() {
            if (index < 0) {
                throw new IllegalStateException("empty !");
            }
            int curr = index;
            index--;
            return (T) array[curr];
        }

        public boolean isEmpty() {
            return index < 0;
        }
    }

    public static class MyQueue2<T> {

        private final int capacity;

        private final Object[] array;

        // ring
        private int highIndex = 0;

        private int lowIndex = 0;

        private int size = 0;

        public MyQueue2(int capacity) {
            this.capacity = capacity;
            this.array = new Object[capacity];
        }

        public void offer(T value) {
            if (getSize() >= capacity) {
                throw new IllegalStateException("over limit !");
            }
            int curr = highIndex;
            if (highIndex < capacity - 1) {
                highIndex++;
            }
            else {
                highIndex = 0;
            }
            size++;
            array[curr] = value;
        }

        public T poll() {
            if (getSize() <= 0) {
                throw new IllegalStateException("empty !");
            }
            int curr = lowIndex;
            if (lowIndex < capacity - 1) {
                lowIndex++;
            }
            else {
                lowIndex = 0;
            }
            size--;
            return (T) array[curr];
        }

        public boolean isEmpty() {
            return getSize() > 0;
        }

        private int getSize() {
            return size;
        }

    }

}
