package xyz.flysium.photon.algorithm.queueandstack.stack.basic;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 225. 用队列实现栈
 * <p>
 * https://leetcode-cn.com/problems/implement-stack-using-queues/
 *
 * @author zeno
 */
public interface T0225_ImplementStackUsingQueues {

  // 你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合法的。
  class MyStack {

    private final Deque<Integer> queue1;
    private final Deque<Integer> queue2;
    private int flag = 1;

    /**
     * Initialize your data structure here.
     */
    public MyStack() {
      queue1 = new LinkedList<>();
      queue2 = new LinkedList<>();
    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
      if (flag == 1) {
        queue1.addLast(x);
      } else {
        queue2.addLast(x);
      }
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
      if (flag == 1) {
        while (queue1.size() > 1) {
          queue2.addLast(queue1.removeFirst());
        }
        flag = 2;
        return queue1.removeFirst();
      } else {
        while (queue2.size() > 1) {
          queue1.addLast(queue2.removeFirst());
        }
        flag = 1;
        return queue2.removeFirst();
      }
    }

    /**
     * Get the top element.
     */
    public int top() {
      int e = pop();
      push(e);
      return e;
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
      return queue1.isEmpty() && queue2.isEmpty();
    }
  }

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */

}
