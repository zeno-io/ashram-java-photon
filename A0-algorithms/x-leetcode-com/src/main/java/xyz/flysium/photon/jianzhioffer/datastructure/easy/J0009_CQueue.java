package xyz.flysium.photon.jianzhioffer.datastructure.easy;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 剑指 Offer 09. 用两个栈实现队列
 * <p>
 * https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/
 *
 * @author zeno
 */
public interface J0009_CQueue {

  // 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
  // 分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
  //

  // 执行用时：55 ms, 在所有 Java 提交中击败了77.14% 的用户
  class CQueue {

    private final Deque<Integer> stack;
    private final Deque<Integer> helper;

    public CQueue() {
      stack = new LinkedList<>();
      helper = new LinkedList<>();
    }

    public void appendTail(int value) {
      stack.push(value);
    }

    public int deleteHead() {
      if (helper.isEmpty()) {
        while (!stack.isEmpty()) {
          helper.push(stack.pop());
        }
      }
      if (helper.isEmpty()) {
        return -1;
      }
      return helper.pop();
    }

  }

/**
 * Your CQueue object will be instantiated and called as such:
 * CQueue obj = new CQueue();
 * obj.appendTail(value);
 * int param_2 = obj.deleteHead();
 */


}
