package xyz.flysium.photon.xalgorithm.easy;

/**
 * 867. 转置矩阵
 * <p>
 * https://leetcode-cn.com/problems/transpose-matrix/
 *
 * @author zeno
 */
public class T0867_TransposeMatrix {

//给定一个矩阵 A， 返回 A 的转置矩阵。
//
// 矩阵的转置是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。
//
//
//
// 示例 1：
//
// 输入：[[1,2,3],[4,5,6],[7,8,9]]
//输出：[[1,4,7],[2,5,8],[3,6,9]]
//
//
// 示例 2：
//
// 输入：[[1,2,3],[4,5,6]]
//输出：[[1,4],[2,5],[3,6]]
//
//
//
//
// 提示：
//
//
// 1 <= A.length <= 1000
// 1 <= A[0].length <= 1000
//
// Related Topics 数组
// 👍 117 👎 0


  public static void main(String[] args) {
    Solution solution = new T0867_TransposeMatrix().new Solution();

  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[][] transpose(int[][] A) {
      if (A == null) {
        throw new IllegalArgumentException();
      }
      final int rows = A.length;
      final int cols = A[0].length;

      if (rows == cols) {
        for (int i = 0; i < rows; i++) {
          for (int j = i + 1; j < cols; j++) {
            swap(A, i, j, j, i);
          }
        }
        return A;
      }
      int[][] ans = new int[cols][rows];

      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          ans[j][i] = A[i][j];
        }
      }
      return ans;
    }

    private void swap(int[][] arr, int x1, int y1, int x2, int y2) {
      if (x1 == x2 && y1 == y2) {
        return;
      }
      arr[x1][y1] = arr[x1][y1] ^ arr[x2][y2];
      arr[x2][y2] = arr[x1][y1] ^ arr[x2][y2];
      arr[x1][y1] = arr[x1][y1] ^ arr[x2][y2];
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
