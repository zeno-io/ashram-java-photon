package xyz.flysium.photon.jianzhioffer.datastructure.easy;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 剑指 Offer 30. 包含 min 函数的栈
 * <p>
 * https://leetcode-cn.com/problems/bao-han-minhan-shu-de-zhan-lcof/
 *
 * @author zeno
 */
public interface J0030_MinStack {

  // 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。

  // 各函数的调用总次数不超过 20000 次

  // 执行用时：21 ms, 在所有 Java 提交中击败了28.12% 的用户

  class MinStack {

    private final Deque<Integer> stack;
    private final Deque<Integer> minStack;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
      stack = new LinkedList<>();
      minStack = new LinkedList<>();
    }

    public void push(int x) {
//      if (stack.isEmpty()) {
//        stack.push(x);
//        minStack.push(x);
//        return;
//      }
//      int min = Math.min(minStack.peek(), x);
      stack.push(x);
      if (minStack.isEmpty() || x <= minStack.peek()) {
        System.out.println(x);
        minStack.push(x);
      }
    }

    public void pop() {
      int e = stack.pop();
      if (e == minStack.peek()) {
        minStack.pop();
      }
    }

    public int top() {
      return stack.peek();
    }

    public int min() {
      return minStack.peek();
    }

  }

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.min();
 */

}
