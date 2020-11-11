package xyz.flysium.photon.algorithm.queueandstack.stack.basic;

import java.util.Stack;

/**
 * 155. 最小栈
 * <p>
 * https://leetcode-cn.com/problems/min-stack/
 *
 * @author zeno
 */
public interface T0155_MinStack {

  // 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
  //    push(x) —— 将元素 x 推入栈中。
  //    pop() —— 删除栈顶的元素。
  //    top() —— 获取栈顶元素。
  //    getMin() —— 检索栈中的最小元素。
  class MinStack {

    private final Stack<Integer> stack;
    private final Stack<Integer> minStack;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
      stack = new Stack<>();
      minStack = new Stack<>();
    }

    public void push(int x) {
      stack.push(x);
      if (minStack.isEmpty()) {
        minStack.push(x);
      } else {
        minStack.push(Math.min(minStack.peek(), x));
      }
    }

    public void pop() {
      stack.pop();
      minStack.pop();
    }

    public int top() {
      return stack.peek();
    }

    public int getMin() {
      return minStack.peek();
    }

  }

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
}
