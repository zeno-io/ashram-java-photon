package xyz.flysium.photon.algorithm.array.dimensional.medium;

/**
 * 498、对角线遍历
 * <p>
 * https://leetcode-cn.com/problems/diagonal-traverse/
 *
 * @author zeno
 */
public class T0498_DiagonalTraverse {

  static class Solution {

    //    每一个对角线中的元素距离左上角的长度都是固定的，对角线个数为 （m-1）+（n-1）, 长度从 0 ~ （m-1）+（n-1）
    //    每个对角线中的元素，如果长度大于等于 n, 从 len - n + 1 开始
    //    如果是偶数长度的对角线，翻转子数组即可。
    public int[] findDiagonalOrder(int[][] matrix) {
      if (matrix.length == 0) {
        return new int[0];
      }
      final int m = matrix.length;
      final int n = matrix[0].length;
      final int size = m * n;
      final int maxLength = (m - 1) + (n - 1);
      int[] ans = new int[size];
      int len = 0, begin = 0, index = 0, tmpIndex = 0;
      while (len <= maxLength) {
        begin = len >= n ? len - n + 1 : 0;
        tmpIndex = index;
        for (int x = begin; x < m && x <= len; x++) {
          ans[index++] = matrix[x][len - x];
        }
        // len % 2 == 1
        if ((len & (~len + 1)) != 1) {
          reverse(ans, tmpIndex, index - 1);
        }
        len++;
      }

      return ans;
    }

    private void reverse(int[] arr, int l, int r) {
      int len = r - l + 1;
      for (int i = 0; i < len >> 1; i++) {
        swap(arr, l + i, r - i);
      }
    }

    private void swap(int[] arr, int a, int b) {
      if (a == b) {
        return;
      }
      arr[a] = arr[a] ^ arr[b];
      arr[b] = arr[a] ^ arr[b];
      arr[a] = arr[a] ^ arr[b];
    }

  }

}
