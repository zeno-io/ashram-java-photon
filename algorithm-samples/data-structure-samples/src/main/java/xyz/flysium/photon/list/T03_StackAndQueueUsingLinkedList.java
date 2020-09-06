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
 * 采用 双向链表实现
 * <p>
 * 栈：数据先进后出，犹如弹匣
 * <p>
 * 队列：数据先进先出，好似排队
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T03_StackAndQueueUsingLinkedList {

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int times = 100000;
        int size = 100;
        int maxValue = 100;

        for (int i = 0; i < times; i++) {
            Stack<Integer> jdkStack = new Stack<>();
            MyStack<Integer> myStack = new MyStack<>();

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
                    else {
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
            MyQueue<Integer> myQueue = new MyQueue<>();

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

    public static class MyStack<T> {

        private final DoubleEndsQueue<T> queue;

        public MyStack() {
            this.queue = new DoubleEndsQueue<>();
        }

        public void push(T value) {
            this.queue.addToHead(value);
        }

        public T pop() {
            return this.queue.removeHead();
        }

        public boolean isEmpty() {
            return this.queue.isEmpty();
        }
    }

    public static class MyQueue<T> {

        private final DoubleEndsQueue<T> queue;

        public MyQueue() {
            this.queue = new DoubleEndsQueue<>();
        }

        public void offer(T value) {
            this.queue.addToHead(value);
        }

        public T poll() {
            return this.queue.removeTail();
        }

        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

    }

    static class DoubleEndsQueue<T> {

        private DoubleNode<T> head = null;

        private DoubleNode<T> tail = null;

        public boolean isEmpty() {
            return head == null;
        }

        public void addToHead(T value) {
            DoubleNode<T> node = new DoubleNode<>(value);
            if (head == null) {
                head = node;
                tail = node;
            }
            else {
                node.next = head;
                head.prev = node;
                head = node;
            }
            head = node;
        }

        public T removeHead() {
            if (head == null) {
                return null;
            }
            DoubleNode<T> curr = head;
            if (head == tail) {
                head = null;
                tail = null;
            }
            else {
                head = head.next;
                head.prev = null;
            }
            curr.next = null;
            curr.prev = null;
            return curr.value;
        }

        public void addToTail(T value) {
            DoubleNode<T> node = new DoubleNode<>(value);
            if (tail == null) {
                head = node;
                tail = node;
            }
            else {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }
        }

        public T removeTail() {
            if (tail == null) {
                return null;
            }
            DoubleNode<T> curr = tail;
            if (head == tail) {
                head = null;
                tail = null;
            }
            else {
                tail = tail.prev;
                tail.next = null;
            }
            curr.next = null;
            curr.prev = null;
            return curr.value;
        }
    }

}
