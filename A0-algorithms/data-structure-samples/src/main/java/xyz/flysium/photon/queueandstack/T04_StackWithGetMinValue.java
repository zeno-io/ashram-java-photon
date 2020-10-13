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

import java.util.Stack;

/**
 * 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能
 * <p>
 * 1）pop、push、getMinValue 操作的时间复杂度都是 O(1)。
 * <p>
 * 2）设计的栈类型可以使用现成的栈结构。
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T04_StackWithGetMinValue {

  public static void main(String[] args) {
    MyStack3 stack = new MyStack3();
    stack.push(3);
    stack.push(4);
    stack.push(7);
    stack.push(2);
    stack.push(5);

    System.out.println("min->" + stack.getMinValue());
    System.out.println(stack.pop());
    System.out.println("min->" + stack.getMinValue());
    System.out.println(stack.pop());
    System.out.println("min->" + stack.getMinValue());
    System.out.println(stack.pop());
    System.out.println("min->" + stack.getMinValue());
    System.out.println(stack.pop());
    System.out.println("min->" + stack.getMinValue());
    System.out.println(stack.pop());
  }

    public static class MyStack3<T extends Comparable<T>> {

        private final Stack<T> dataStack;

        private final Stack<T> minStack;

        public MyStack3() {
            this.dataStack = new Stack<>();
            this.minStack = new Stack<>();
        }

        public void push(T value) {
            this.dataStack.push(value);
            if (this.minStack.isEmpty()) {
                this.minStack.push(value);
            }
            else {
                T minPeek = this.minStack.peek();
                this.minStack.push(minPeek.compareTo(value) < 0 ? minPeek : value);
            }
        }

        public T pop() {
            this.minStack.pop();
            return this.dataStack.pop();
        }

        public T getMinValue() {
            return this.minStack.peek();
        }

        public boolean isEmpty() {
            return this.dataStack.isEmpty();
        }
    }

}
