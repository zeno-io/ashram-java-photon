package xyz.flysium.photon.algorithm.string.basic;

/**
 * 344. 反转字符串
 * <p>
 * https://leetcode-cn.com/problems/reverse-string/
 *
 * @author zeno
 */
public interface U0344_ReverseString {

// 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
// 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
// 你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。

  class Solution {

    public void reverseString(char[] s) {
      final int n = s.length;

      for (int i = 0; i < n >> 1; i++) {
        swap(s, i, n - 1 - i);
      }
    }

    private void swap(char[] c, int x, int y) {
      if (x == y) {
        return;
      }
      char tmp = c[x];
      c[x] = c[y];
      c[y] = tmp;
    }

  }

}
