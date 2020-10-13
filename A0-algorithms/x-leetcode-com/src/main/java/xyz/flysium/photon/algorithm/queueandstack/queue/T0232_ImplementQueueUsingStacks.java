package xyz.flysium.photon.algorithm.queueandstack.queue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 232. 用栈实现队列
 * <p>
 * https://leetcode-cn.com/problems/implement-queue-using-stacks/
 *
 * @author zeno
 */
public interface T0232_ImplementQueueUsingStacks {

  // 你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
  class MyQueue {

    private final Deque<Integer> stack;
    private final Deque<Integer> helper;

    /**
     * Initialize your data structure here.
     */
    public MyQueue() {
      stack = new LinkedList<>();
      helper = new LinkedList<>();
    }

    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
      stack.push(x);
    }

    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
      if (helper.isEmpty()) {
        while (!stack.isEmpty()) {
          helper.push(stack.pop());
        }
      }
      return helper.pop();
    }

    /**
     * Get the front element.
     */
    public int peek() {
      if (helper.isEmpty()) {
        while (!stack.isEmpty()) {
          helper.push(stack.pop());
        }
      }
      return helper.peek();
    }

    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {
      return helper.isEmpty() && stack.isEmpty();
    }

  }

}
