package xyz.flysium.photon.algorithm.queueandstack.search.basic;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 752. 打开转盘锁
 * <p>
 * https://leetcode-cn.com/problems/open-the-lock/
 *
 * @author zeno
 */
public interface T0752_OpenTheLock {

  // 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。
  // 每个拨轮可以自由旋转：例如把 '9' 变为  '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
  //
  // 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
  //
  // 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
  //
  // 字符串 target 代表可以解锁的数字，你需要给出最小的旋转次数，如果无论如何不能解锁，返回 -1。

  // 91ms BFS
  class Solution {

    public int openLock(String[] deadends, String target) {
      if ("0000".equals(target)) {
        return 0;
      }
      // 记录需要跳过的死亡密码
      Set<String> deadendSet = new HashSet<>(Arrays.asList(deadends));
      // 记录已经穷举过的密码，防止走回头路
      Set<String> visited = new HashSet<>();
      Deque<String> queue = new LinkedList<>();
      int depth = 0;

      queue.offerLast("0000");
      visited.add("0000");
      while (!queue.isEmpty()) {
        int sz = queue.size();
        for (int x = 0; x < sz; x++) {
          String node = queue.pollFirst();
          if (deadendSet.contains(node)) {
            continue;
          }
          if (target.equals(node)) {
            return depth;
          }
          for (int k = 0; k < 2; k++) {
            for (int i = 0; i < 4; i++) {
              char[] cs = node.toCharArray();
              if (k == 0) {
                cs[i] = ('9' == cs[i]) ? '0' : (char) (cs[i] + 1);
              } else {
                cs[i] = ('0' == cs[i]) ? '9' : (char) (cs[i] - 1);
              }
              String next = String.valueOf(cs);
              if (visited.contains(next)) {
                continue;
              }
              visited.add(next);
              queue.offerLast(next);
            }
          }
        }
        depth++;
      }
      return -1;
    }

  }

}
